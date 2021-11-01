package jw.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface FluentCommandConsoleEvent
{

    void execute(ConsoleCommandSender player, String[] args);
}
