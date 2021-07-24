package jw.commands;

import org.bukkit.entity.Player;

public interface CommandEvent
{
     void execute(Player player,String[] args);
}
