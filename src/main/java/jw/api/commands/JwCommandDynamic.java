package jw.api.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class JwCommandDynamic extends JwCommand
{

    private CommandEvent commandEvent;

    private boolean runByConsle =false;
    public JwCommandDynamic(String name, CommandEvent commandEvent)
    {
        super(name,false);
        this.commandEvent = commandEvent;
    }
    public JwCommandDynamic(String name, CommandEvent commandEvent,boolean runByConsle)
    {
        this(name,commandEvent);
        this.runByConsle = runByConsle;
    }
    @Override
    public void Invoke(Player playerSender, String[] args) {
        commandEvent.Execute(playerSender,args);
    }

    @Override
    public void Invoke(ConsoleCommandSender serverSender, String[] args) {
       if(this.runByConsle)
        commandEvent.Execute(null,args);
    }

    @Override
    public void OnInitialize() {

    }
}
