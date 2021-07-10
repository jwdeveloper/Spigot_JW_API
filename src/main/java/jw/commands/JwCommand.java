package jw.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class JwCommand extends BukkitCommand {

    private JwCommand parent;
    private String premission = "";
    private String noPermissionMessage;
    private boolean permissionSet;
    private ArrayList<JwCommand> childs = new ArrayList<>();
    private HashMap<Integer, Supplier<ArrayList<String>>> tabCompletes = new HashMap<>();
    private ArrayList<String> empty_tabCompletes = new ArrayList<>();
    private Consumer<CommandSender> OnNoArguments;
    protected boolean commandResult = true;
    
    public abstract void Invoke(Player playerSender, String[] args);

    public abstract void Invoke(ConsoleCommandSender serverSender, String[] args);

    public abstract void OnInitialize();

    public JwCommand(String name, boolean registerCommand) {
        super(name);
        if (registerCommand)
            RegisterCommands();
        OnInitialize();
    }

    public JwCommand(String name) {
        super(name);
        RegisterCommands();
        OnInitialize();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        JwCommand target = this;
        String[] arguments = args;
        if (args.length != 0) {
            JwCommand child_invoked = target.IsChildInvoked(args);
            if (child_invoked != null) {
                target = child_invoked;
                for (int j = 0; j < args.length; j++) {
                    if (args[j].equalsIgnoreCase(target.getName())) {
                        arguments = Arrays.copyOfRange(args, j + 1, args.length);
                        break;
                    }
                }
            }

        }
        return target.GetTabCompleter(arguments.length);
    }

    public void SetCommandResult(boolean result) {
        this.commandResult = result;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        JwCommand target = this;
        String[] arguments = args;
        commandResult = true;
        if (args.length != 0) {
            //null nie jest najlepszym rozwiazaniem, ale komu by sie chcialo robic opcjonala
            //magia połaczona z rekurencją
            JwCommand child_invoked = target.IsChildInvoked(args);
            if (child_invoked != null) {
                target = child_invoked;

                for (int j = 0; j < args.length; j++) {
                    if (args[j].equalsIgnoreCase(target.getName())) {
                        arguments = Arrays.copyOfRange(args, j + 1, args.length);
                        break;
                    }
                }
            }

        }
        if (target.OnNoArguments != null && arguments.length == 0) {
            target.OnNoArguments.accept(commandSender);
            return commandResult;
        }

        if (commandSender instanceof Player) {
            //sprawdzanie permisji
            if (target.permissionSet && !((Player) commandSender).hasPermission(target.premission))
            {
                commandSender.sendMessage(target.noPermissionMessage + ": " + target.premission);
                return commandResult;
            }

            target.Invoke((Player) commandSender, arguments);
        } else {
            target.Invoke((ConsoleCommandSender) commandSender, arguments);
        }
        return commandResult;
    }

    public String GetMessage(String[] args) {
        StringBuilder toReturn = new StringBuilder();
        for (String s : args) {
            toReturn.append(s);
            toReturn.append(' ');
        }

        return toReturn.toString();
    }

    //rekurencja bejbe
    public JwCommand IsChildInvoked(String[] args) {
        JwCommand result = null;
        for (JwCommand c : childs) {
            //szukanie komendy wsrod dzieci
            if (args.length > 1) {
                String[] part = Arrays.copyOfRange(args, 1, args.length);

                result = c.IsChildInvoked(part);

                if (result != null) {
                    break;
                }
            }
            // jesli nie znaleziono udzieci to moze rodzic jest wlasicielm komendy
            if (c.getName().equalsIgnoreCase(args[0])) {
                result = c;
                break;
            }
        }
        return result;
    }

    public void AddPermission(String name, String error) {
        this.premission = name;
        this.noPermissionMessage = error;
        this.permissionSet = true;
    }

    public void AddPermission(String error) {

        String result = this.getName();
        JwCommand command = this.parent;
        while (command != null) {
            result = command.getName() + "." + result;
        }
        this.premission = result;
        this.noPermissionMessage = error;
        this.permissionSet = true;
    }


    public ArrayList<String> GetTabCompleter(int argument) {
        return tabCompletes.getOrDefault(argument, () -> {
            return empty_tabCompletes;
        }).get();
    }

    public void AddTabCompleter(int argument, String... acction) {
        ArrayList<String> complieters = new ArrayList<>();

        for (int i = 0; i < acction.length; i++) {
            complieters.add(acction[i]);
        }

        tabCompletes.putIfAbsent(argument, () -> {
            return complieters;
        });
    }


    public void DisplayChildsName() {
        tabCompletes.putIfAbsent(1, () ->
        {
            ArrayList<String> names = new ArrayList<>();

            this.childs.forEach(c -> {
                names.add(c.getName());
            });

            return names;
        });
    }

    public void SetParent(JwCommand parent)
    {
        this.parent = parent;
    }

    public void SetNoArgsError(Consumer<CommandSender> acction) {
        OnNoArguments = acction;
    }

    public void AddTabCompleter(int argument, Supplier<ArrayList<String>> acction) {
        tabCompletes.putIfAbsent(argument, acction);
    }

    public void AddChild(JwCommand child) {
        child.SetParent(this);
        childs.add(child);
    }
    
    public void AddChild(String name,CommandEvent commandEvent)
    {
        this.AddChild(new JwCommandDynamic(name,commandEvent));
    }
    
    public void RemoveChild(JwCommand child) {
        child.parent = null;
        childs.remove(child);
    }
    public String ConnectArgs(String[] stringArray) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }
    private void RegisterCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(this.getName(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}