package jw.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface FluentCommandEvent
{
     void execute(Player player,String[] args);

}
