package jw.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FluentCommands {
    public static FluentCommand onPlayerCommand(String name, FluentCommandEvent fluentCommandEvent) {
        return new FluentCommand(name) {
            @Override
            public void onInvoke(Player playerSender, String[] args) {
                fluentCommandEvent.execute(playerSender, args);
            }

            @Override
            public void onInvoke(ConsoleCommandSender serverSender, String[] args) {

            }

            @Override
            public void onInitialize() {

            }
        };
    }

    public static FluentCommand onConsoleCommand(String name, FluentCommandConsoleEvent fluentCommandEvent) {
        return new FluentCommand(name) {
            @Override
            public void onInvoke(Player playerSender, String[] args) {

            }

            @Override
            public void onInvoke(ConsoleCommandSender serverSender, String[] args) {
                fluentCommandEvent.execute(serverSender, args);
            }

            @Override
            public void onInitialize() {

            }
        };
    }
}
