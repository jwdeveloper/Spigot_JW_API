package jw.gui.button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public  class ButtonFactory {



    public static ArrayList<Button> FromStringArray(ArrayList<String> list) {

        ArrayList<Button> result = new ArrayList<>();

        list.forEach(l ->
        {
            result.add(new Button(Material.PAPER, l));
        });
        return result;
    }

    public static Button GetBackground(Material material) {
        Button gui_icon = new Button(material);
        gui_icon.SetName(" ");
        gui_icon.setAction(ButtonActionsEnum.BACKGROUND);
        return gui_icon;
    }

    public static Button ExitButton() {
        Button itemstack = new Button(Material.TIPPED_ARROW, "Back", ButtonActionsEnum.EXIT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();

        PotionData data = new PotionData(PotionType.STRENGTH, false, false);
        meta.setBasePotionData(data);

        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static Button EmptyButton() {
        return new Button(Material.SUNFLOWER, " ", ButtonActionsEnum.EMPTY);
    }

    public static Button DeleteButton() {
        return new Button(Material.BARRIER, "Delete", ButtonActionsEnum.DELETE);
    }

    public static Button InsertButton() {
        return new Button(Material.LEGACY_BOOK_AND_QUILL, "Insert", ButtonActionsEnum.INSERT);
    }
    public static Button EditButton() {
        return new Button(Material.BOOKSHELF, "Edit", ButtonActionsEnum.EDIT);
    }

    public static Button CopyButton() {
        return new Button(Material.SLIME_SPAWN_EGG, "Copy", ButtonActionsEnum.COPY);
    }

    public static Button SearchButton() {
        return new Button(Material.SPYGLASS, "Search", ButtonActionsEnum.SEARCH);
    }
    public static Button CancelButton() {
        return new Button(Material.WITHER_SKELETON_SKULL, "Cancel", ButtonActionsEnum.CANCEL);
    }
    public static Button DeleteALLButton() {
        return new Button(Material.TNT, "Delete all", ButtonActionsEnum.DELETE);
    }

    public static Button NextButton(PotionType color) {
        Button itemstack = new Button(Material.TIPPED_ARROW, ChatColor.GREEN + "Next", ButtonActionsEnum.RIGHT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static Button BackButton(PotionType color) {
        Button itemstack = new Button(Material.TIPPED_ARROW, ChatColor.GREEN + "previous", ButtonActionsEnum.LEFT);
        PotionMeta meta = (PotionMeta) itemstack.getItemMeta();
        meta.setBasePotionData(new PotionData(color));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

}