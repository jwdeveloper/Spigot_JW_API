package jw.api.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandHelper
{
    public static void InvokeCommand(Player player, String command)
    {

        if(command.startsWith("[s]"))
        {
            command = command.replace("[s]","");
            command = command.replaceAll("\\[p]",player.getName());

            if(command.contains("say"))
            {
                command = command.replaceAll("&","§");
                command = command.replaceAll("say","");
                String finalCommand = command;

                Bukkit.getOnlinePlayers().forEach(p ->
                {
                    p.sendMessage(finalCommand);
                });
                return;
            }
            else
            {
                boolean result  =Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }

            return;
        }
        command = command.replaceAll("\\[p]",player.getName());
        if(command.contains("say"))
        {
            command = command.replaceAll("&","§");
            command = command.replaceAll("say","");
            player.sendMessage(command);
            return;
        }
        if(command.contains("show"))
        {
            command = command.replaceAll("&","§");
            command = command.replaceAll("show","");
          //  GUI_Title.sendTitle(player, 20, 40, 20,"", command);
            return;
        }
        boolean result  = Bukkit.getServer().dispatchCommand(player, command);
    }

    public static boolean Invoke(Player player, String command)
    {

        if(command == null || command.equals("") || command.equals(" "))
            return false;

        if(command.startsWith("[s]"))
        {
            command = command.replace("[s]","");
            command = command.replaceAll("\\[p]",player.getName());

            if(command.contains("say"))
            {
                command = command.replaceAll("&","§");
                command = command.replaceAll("say","");
                String finalCommand = command;

                Bukkit.getOnlinePlayers().forEach(p ->
                {
                    p.sendMessage(finalCommand);
                });
                return true;
            }
            else
            {
                return Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        }
        command = command.replaceAll("\\[p]",player.getName());
        if(command.contains("say"))
        {
            command = command.replaceAll("&","§");
            command = command.replaceAll("say","");
            player.sendMessage(command);
            return true;
        }
        if(command.contains("show"))
        {
            command = command.replaceAll("&","§");
            command = command.replaceAll("show","");
           // GUI_Title.sendTitle(player, 20, 40, 20,"", command);
            return true;
        }

        return  Bukkit.getServer().dispatchCommand(player, command);



    }
}
