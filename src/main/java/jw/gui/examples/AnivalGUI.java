package jw.gui.examples;

import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class AnivalGUI extends InventoryGUI
{
    public AnivalGUI(String name)
    {
        super(name, 6,InventoryType.ANVIL);
        this.enableLogs(true);
    }

    @Override
    public void onClick(Player player, Button button) {

    }

    @Override
    public void onRefresh(Player player)
    {

    }

    @Override
    public void onOpen(Player player)
    {
        this.addButton(new Button(Material.DIAMOND_SWORD),0);
        this.addButton(new Button(Material.DIAMOND_SWORD),2);
    }

    @Override
    public void onClose(Player player) {

    }

    @Override
    protected void doClick(Player player, int index, ItemStack itemStack) {
      this.displayLog(" "+index+" "+itemStack.toString(), ChatColor.GREEN);
    }
}
