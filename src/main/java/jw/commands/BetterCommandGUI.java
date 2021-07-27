package jw.commands;

import jw.gui.core.InventoryGUI;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class BetterCommandGUI extends BetterCommand {

    private final HashMap<UUID, InventoryGUI> playersGui = new HashMap<UUID, InventoryGUI>();

    public BetterCommandGUI(String name) {
        super(name);
    }

    public abstract InventoryGUI setInventoryGUI();

    public void onInvoke(Player playerSender, String[] args)
    {
        this.getGUI(playerSender).open(playerSender);
    }

    public void onInvoke(ConsoleCommandSender serverSender, String[] args)
    {

    }

    public abstract void onInitialize();

    public void closeGUI() {
        playersGui.forEach((a, b) ->
        {
            playersGui.get(a).close();
        });
    }

    public InventoryGUI getGUI(Player player) {
        if (playersGui.containsKey(player.getUniqueId()))
            return playersGui.get(player.getUniqueId());

        InventoryGUI gui = setInventoryGUI();
        gui.setPlayer(player);
        playersGui.put(player.getUniqueId(), gui);
        return gui;
    }


}
