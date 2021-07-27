package jw.gui.examples;

import jw.gui.core.InventoryGUI;
import jw.gui.examples.chestgui.ListGUI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class SingleInstanceGUI<T extends InventoryGUI>
{
    protected HashMap<UUID, T> playersGUI = new HashMap<>();

    public T getGUI(Player player) {
        if (playersGUI.containsKey(player.getUniqueId()))
            return playersGUI.get(player.getUniqueId());

        T gui = setGUI();
        gui.setPlayer(player);
        playersGUI.put(player.getUniqueId(), gui);
        return gui;
    }

    public abstract T setGUI();
}
