package jw.gui.core;

import jw.InicializerAPI;
import jw.gui.examples.VillagerGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

import java.util.ArrayList;

public class InventoryGUIEventsHander implements Listener {
    private static InventoryGUIEventsHander instnace;
    private final ArrayList<InventoryGUI> inventoriesGui = new ArrayList();

    public static InventoryGUIEventsHander Instnace() {
        if (instnace == null) {
            instnace = new InventoryGUIEventsHander();
        }
        return instnace;
    }

    public InventoryGUIEventsHander() {
        Bukkit.getPluginManager().registerEvents(this, InicializerAPI.getPlugin());
    }

    public void register(InventoryGUI InventoryGUIBase) {
        if (!inventoriesGui.contains(InventoryGUIBase)) {
            inventoriesGui.add(InventoryGUIBase);
        }
    }

    public void unregister(InventoryGUI InventoryGUIBase) {
        inventoriesGui.remove(InventoryGUIBase);
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event)
    {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui)
        {
            inventory = inventoryGUI.inventory;

            if(inventory == null && event.getInventory() instanceof MerchantInventory)
            {
                final VillagerGUI villagerGUI = (VillagerGUI)inventoryGUI;
                final MerchantInventory merchantInventory =(MerchantInventory)event.getInventory();
                if(merchantInventory.getMerchant() == villagerGUI.getMerchant())
                {
                    inventory = merchantInventory;
                    villagerGUI.setInventory(inventory);
                }
            }

            if (inventory == null || !inventoryGUI.isOpen()) continue;
            if (event.getInventory() == inventory) {
                inventoryGUI.refresh();
                break;
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory;
        for (InventoryGUI inventoryGUI : inventoriesGui) {
            inventory = inventoryGUI.inventory;
            if (inventory == null || !inventoryGUI.isOpen()) continue;

            if (event.getInventory() == inventory) {
                inventoryGUI.close();
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999)
            return;

        Inventory inventory;
            for (InventoryGUI inventoryGUI : inventoriesGui)
            {
                inventory = inventoryGUI.inventory;
                if (inventory == null || !inventoryGUI.isOpen()) continue;

                if (event.getInventory() == inventory)
                {
                    event.setCancelled(true);
                    final ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
                        inventoryGUI.doClick((Player)event.getWhoClicked(), event.getRawSlot(), clickedItem);
                    break;
                }
            }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for(int i=0;i<inventoriesGui.size();i++)
        {
            if (event.getPlayer() ==   inventoriesGui.get(i).player) {
                inventoriesGui.get(i).close();
                this.unregister(inventoriesGui.get(i));
                return;
            }
        }
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(InicializerAPI.getPlugin()))
        {
            InventoryGUI inventoryGUI = null;
            for(int i=0;i<inventoriesGui.size();i++)
            {
                inventoriesGui.get(i).close();
            }
        }
    }
    @EventHandler
    private void OnTradeSelected(TradeSelectEvent event)
    {
        for (InventoryGUI inventoryGUI : inventoriesGui)
        {
           if(inventoryGUI.getType() == InventoryType.MERCHANT)
           {
               inventoryGUI.doClick((Player)event.getWhoClicked(), event.getIndex(), null);
           }
        }
    }

}