package jw.gui.examples.chestgui;

import jw.InicializerAPI;
import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import jw.gui.events.InventoryEvent;
import jw.gui.examples.SingleInstanceGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

public class SelectListGUI extends SingleInstanceGUI<ListGUI> {
    public enum SearchType {
        Players, Materials, PlayerInventory, File, Block
    }

    public static SelectListGUI Instnace() {
        if (Instnace == null) {
            Instnace = new SelectListGUI();
        }
        return Instnace;
    }

    private static SelectListGUI Instnace;
    private ArrayList<Button> materials;
    private ArrayList<Button> players_buttons;

    @Override
    public ListGUI setGUI() {
        ListGUI listGUI = new ListGUI(null, "GUI list", 6);
        listGUI.SelectiveMode();
        return listGUI;
    }

    public static ListGUI Get(Player player, String title, ArrayList<Button> items, InventoryEvent acction) {
        ListGUI gui_list = Instnace().getGUI(player);
        gui_list.SetName(title);
        gui_list.SelectItem(acction);
        gui_list.ClearItems();
        gui_list.AddButtons(items);
        return gui_list;
    }

    public static ListGUI Get(Player player, SearchType searchType, InventoryEvent acction) {
        ListGUI gui_list = Instnace().getGUI(player);
        ArrayList<Button> buttons = new ArrayList<>();
        switch (searchType) {
            case Materials:
                gui_list.SetName("Select material");
                gui_list.ClearItems();
                gui_list.AddButtons(Instnace().GetMaterials());
                break;
            case Block:
                gui_list.SetName("Select material");
                gui_list.ClearItems();
                gui_list.AddButtons(Instnace().GetMaterials().stream().filter(e ->
                {
                    Material material = e.GetHoldingObject();
                    if (material.isBlock())
                        return true;
                    else
                        return false;
                }).collect(Collectors.toList()));

                break;

            case Players:

                gui_list.SetName("Select player");
                gui_list.ClearItems();
                gui_list.AddButtons(Instnace().GetPlayers());
                break;
            case PlayerInventory:

                PlayerInventory inventory = player.getInventory();
                Button button;
                for (int i = 0; i < 90; i++) {
                    if (inventory.getItem(i) != null) {
                        button = Button.fromItemStack(inventory.getItem(i));
                        button.setObjectHolder(inventory.getItem(i));
                        buttons.add(button);
                    }
                }
                gui_list.SetName("Select item");
                gui_list.ClearItems();
                gui_list.AddButtons(buttons);
                break;
        }
        gui_list.SelectItem(acction);
        return gui_list;
    }

    private ArrayList<Button> GetMaterials() {
        if (materials == null) {
            materials = new ArrayList<>();
            Arrays.stream(Material.values()).forEach(material ->
            {
                Button button = new Button(material, material.toString());
                button.setObjectHolder(material);
                materials.add(button);
            });
        }
        return materials;
    }

    private ArrayList<Button> GetPlayers() {
        if (players_buttons == null) {
            players_buttons = new ArrayList<>();
            Arrays.stream(Bukkit.getServer().getOfflinePlayers()).forEach(e ->
            {
                Button Button = new Button(Material.PLAYER_HEAD, e.getName());
                Button.setTag(e.getUniqueId().toString());
                Button.setObjectHolder(e);
                players_buttons.add(Button);
                Bukkit.getScheduler().runTask(InicializerAPI.GetPlugin(), () ->
                {
                    SkullMeta meta = (SkullMeta) Button.getItemMeta();
                    meta.setOwner(e.getName());
                    Button.setItemMeta(meta);
                });
            });
        }

        return players_buttons;


    }

}