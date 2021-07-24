package jw.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BetterSubCommand extends BetterCommand
{

    private CommandEvent commandEvent;

    private boolean runByConsle =false;
    public BetterSubCommand(String name, CommandEvent commandEvent)
    {
        super(name,false);
        this.commandEvent = commandEvent;
    }
    public BetterSubCommand(String name, CommandEvent commandEvent, boolean runByConsle)
    {
        this(name,commandEvent);
        this.runByConsle = runByConsle;
    }
    @Override
    public void invoke(Player playerSender, String[] args) {
        commandEvent.execute(playerSender,args);
    }

    @Override
    public void invoke(ConsoleCommandSender serverSender, String[] args) {
       if(this.runByConsle)
        commandEvent.execute(null,args);
    }

    @Override
    public void onInitialize() {

    }
}
