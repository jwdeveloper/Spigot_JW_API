package jw.gui.core;

import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class InventoryGUIEventsHander implements Listener {
    private static InventoryGUIEventsHander instnace;
    private ArrayList<InventoryGUI> inventoriesGui = new ArrayList();

    public static InventoryGUIEventsHander Instnace() {
        if (instnace == null) {
            instnace = new InventoryGUIEventsHander();
        }
        return instnace;
    }

    public InventoryGUIEventsHander() {
        Bukkit.getPluginManager().registerEvents(this, InicializerAPI.GetPlugin());
    }


    public void Register(InventoryGUI InventoryGUIBase) {
        if (!inventoriesGui.contains(InventoryGUIBase)) {
            inventoriesGui.add(InventoryGUIBase);
        }
    }

    public void Unregister(InventoryGUI InventoryGUIBase) {
        inventoriesGui.remove(InventoryGUIBase);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui) {
            inventory = inventoryGUI.inventory;
            //Bukkit.getConsoleSender().sendMessage("Inventoyrs "+event.getInventory().toString()+ " " +inventory.toString());
            if (inventory == null || !inventoryGUI.isOpen) continue;
            // Bukkit.getConsoleSender().sendMessage("Open "+members.get(i).getCurrentTitle()+ " "+members.size());
            if (event.getInventory() == inventory) {
                inventoryGUI.Refresh();
                break;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui) {
            inventory = inventoryGUI.inventory;
            if (inventory == null || !inventoryGUI.isOpen) continue;

            if (event.getInventory() == inventory) {
                inventoryGUI.Stop_tasks();
                inventoryGUI.Close();
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999)
            return;

        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui) {
            inventory = inventoryGUI.inventory;
            if (inventory == null || !inventoryGUI.isOpen) continue;

            if (event.getInventory() == inventory) {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
                inventoryGUI.DoClick((Player) event.getWhoClicked(), event.getRawSlot(), clickedItem);
                break;
            }
        }

    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        for(int i=0;i<inventoriesGui.size();i++)
        {
            if (event.getPlayer() ==   inventoriesGui.get(i).player) {
                inventoriesGui.get(i).Stop_tasks();
                inventoriesGui.get(i).Close();
                this.Unregister( inventoriesGui.get(i));
                return;
            }
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(InicializerAPI.GetPlugin()))
        {
            InventoryGUI inventoryGUI = null;
            for(int i=0;i<inventoriesGui.size();i++)
            {
                inventoriesGui.get(i).Stop_tasks();
                inventoriesGui.get(i).Close();
            }
        }
    }

}