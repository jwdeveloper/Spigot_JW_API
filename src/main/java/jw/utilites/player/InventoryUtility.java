package jw.utilites.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryUtility
{

    public static HashMap<Integer, ItemStack> copyInventory(Player player)
    {
        HashMap<Integer, ItemStack> result = new HashMap<>();

        for(int i=0; i<player.getInventory().getSize();i++)
        {
            result.put(i, player.getInventory().getItem(i).clone());
        }
        return  result;
    }
    public static void clearInventory(Player player)
    {
        fillInventoryWithMaterial(player, Material.AIR);
    }

    public static void fillInventoryWithMaterial(Player player,Material material)
    {
        for(int i=0; i<player.getInventory().getSize();i++)
        {
            player.getInventory().setItem(i, new ItemStack(material));
        }
    }

    public static void fillInventoryWithMaterials(Player player,HashMap<Integer, ItemStack> items)
    {
        items.forEach((a,b)->
        {
            player.getInventory().setItem(a,b);
        });
    }



}