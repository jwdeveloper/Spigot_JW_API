package jw.gui.assets;

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
        this.enableLogs =true;
    }

    @Override
    public void OnClick(Player player, Button button) {

    }

    @Override
    public void OnRefresh(Player player)
    {

    }

    @Override
    public void OnOpen(Player player)
    {
        this.AddButton(new Button(Material.DIAMOND_SWORD),0);
        this.AddButton(new Button(Material.DIAMOND_SWORD),2);
    }

    @Override
    public void OnClose(Player player) {

    }

    @Override
    protected void DoClick(Player player, int index, ItemStack itemStack) {
      this.DisplayLog(" "+index+" "+itemStack.toString(), ChatColor.GREEN);
    }
}
