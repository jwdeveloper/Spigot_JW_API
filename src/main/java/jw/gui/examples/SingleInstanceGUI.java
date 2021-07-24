package jw.gui.examples;

import jw.gui.core.InventoryGUI;
import jw.gui.examples.chestgui.ListGUI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class SingleInstanceGUI<T extends InventoryGUI>
{
    protected HashMap<UUID, T> guiHashMap = new HashMap<>();

    public T getGUI(Player player) {
        if (guiHashMap.containsKey(player.getUniqueId()))
            return guiHashMap.get(player.getUniqueId());

        T gui = setGUI();
        gui.setPlayer(player);
        guiHashMap.put(player.getUniqueId(), gui);
        return gui;
    }

    public abstract T setGUI();
}
