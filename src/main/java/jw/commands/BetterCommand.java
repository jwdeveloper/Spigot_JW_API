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

public abstract class BetterCommand extends BukkitCommand {

    private BetterCommand parent;
    private String permission = "";
    private String noPermissionMessage;
    private boolean permissionSet;
    private final ArrayList<BetterCommand> children = new ArrayList<>();
    private final HashMap<Integer, Supplier<ArrayList<String>>> tabCompletes = new HashMap<>();
    private final ArrayList<String> emptyTabCompletes = new ArrayList<>();
    private Consumer<CommandSender> OnNoArguments;
    protected boolean commandResult = true;
    
    public abstract void onInvoke(Player playerSender, String[] args);

    public abstract void onInvoke(ConsoleCommandSender serverSender, String[] args);

    public abstract void onInitialize();

    public BetterCommand(String name, boolean registerCommand) {
        super(name);
        if (registerCommand)
            registerCommands();
        onInitialize();
    }

    public BetterCommand(String name) {
        super(name);
        registerCommands();
        onInitialize();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        BetterCommand target = this;
        String[] arguments = args;
        if (args.length != 0) {
            BetterCommand child_invoked = target.isChildInvoked(args);
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
        return target.getTabCompleter(arguments.length);
    }

    public void setCommandResult(boolean result) {
        this.commandResult = result;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        BetterCommand target = this;
        String[] arguments = args;
        commandResult = true;
        if (args.length != 0) {
            //null nie jest najlepszym rozwiazaniem, ale komu by sie chcialo robic opcjonala
            //magia połaczona z rekurencją
            BetterCommand child_invoked = target.isChildInvoked(args);
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
            if (target.permissionSet && !((Player) commandSender).hasPermission(target.permission))
            {
                commandSender.sendMessage(target.noPermissionMessage + ": " + target.permission);
                return commandResult;
            }
            target.onInvoke((Player) commandSender, arguments);
        } else {
            target.onInvoke((ConsoleCommandSender) commandSender, arguments);
        }
        return commandResult;
    }

    public String getMessage(String[] args) {
        StringBuilder toReturn = new StringBuilder();
        for (String s : args) {
            toReturn.append(s);
            toReturn.append(' ');
        }

        return toReturn.toString();
    }

    //rekurencja bejbe
    public BetterCommand isChildInvoked(String[] args) {
        BetterCommand result = null;
        for (BetterCommand c : children) {
            //szukanie komendy wsrod dzieci
            if (args.length > 1) {
                String[] part = Arrays.copyOfRange(args, 1, args.length);
                result = c.isChildInvoked(part);
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

    public void setPermission(String name, String error) {
        this.permission = name;
        this.noPermissionMessage = error;
        this.permissionSet = true;
    }

    public void setPermission(String error) {

        String result = this.getName();
        BetterCommand command = this.parent;
        while (command != null) {
            result = command.getName() + "." + result;
        }
        this.permission = result;
        this.noPermissionMessage = error;
        this.permissionSet = true;
    }


    public ArrayList<String> getTabCompleter(int argument) {
        return tabCompletes.getOrDefault(argument, () -> {
            return emptyTabCompletes;
        }).get();
    }

    public void setTabCompleter(int argument, String... acction) {
        ArrayList<String> complieters = new ArrayList<>();

        for (int i = 0; i < acction.length; i++) {
            complieters.add(acction[i]);
        }

        tabCompletes.putIfAbsent(argument, () -> {
            return complieters;
        });
    }

    public void displaySubCommandsNames() {
        tabCompletes.putIfAbsent(1, () ->
        {
            ArrayList<String> names = new ArrayList<>();

            this.children.forEach(c -> {
                names.add(c.getName());
            });

            return names;
        });
    }

    public void setParent(BetterCommand parent)
    {
        this.parent = parent;
    }

    public void setNoArgsError(Consumer<CommandSender> acction) {
        OnNoArguments = acction;
    }

    public void setTabCompleter(int argument, Supplier<ArrayList<String>> acction) {
        tabCompletes.putIfAbsent(argument, acction);
    }

    public void addChild(BetterCommand child) {
        child.setParent(this);
        children.add(child);
    }
    
    public void addChild(String name,CommandEvent commandEvent)
    {
        this.addChild(new BetterSubCommand(name,commandEvent));
    }
    
    public void removeChild(BetterCommand child) {
        child.parent = null;
        children.remove(child);
    }
    protected String connectArgs(String[] stringArray) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }
    private void registerCommands() {
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