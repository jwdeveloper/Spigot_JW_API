package jw.gui.examples;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Lectern;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftLectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.bukkit.inventory.meta.BookMeta;

public class BookGUI extends ChestGUI
{
    private Lectern lectern;
    Block block;
    public BookGUI() {
        super("Book", 6);

    }

    public LecternInventory getLectern()
    {
        return (LecternInventory)lectern.getInventory();
    }
    @Override
    public void onInitialize() {

    }
    @Override
    public void onOpen(Player player) {
       getLectern();
    }

    @Override
    public void onClose(Player player) {
        Bukkit.getConsoleSender().sendMessage("Close");
    }
    @Override
    public void open(Player player)
    {
        createLectern(player);
        super.open(player);
    }
    private void createLectern(Player player)
    {
        if(lectern == null)
        {
          Location location = player.getLocation().add(0,5,0);
          block = location.getBlock();
          block.setType(Material.LECTERN);
          lectern = (Lectern) block.getState();
        }
    }

    public void setBook(ItemStack book)
    {
        CraftLectern craftLec = (CraftLectern)block.getState();
        craftLec.getInventory().addItem(book);
    }
    @Override
    protected Inventory createInventory(InventoryType inventoryType)
    {
        return lectern.getInventory();
    }
}
