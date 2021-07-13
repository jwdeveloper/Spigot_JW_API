package jw.gui.assets.chestgui;

import jw.InicializerAPI;
import jw.gui.button.Button;
import jw.gui.events.InventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class SelectListGUI
{
    private static SelectListGUI Instnace;

    public  enum SearchType
    {
        Players,Materials,PlayerInventory,File,Block
    }
    public static SelectListGUI Instnace()
    {
        if(Instnace==null)
        {
            Instnace = new SelectListGUI();
        }
        return Instnace;
    }
    private   HashMap<UUID, ListGUI> players = new HashMap<>();
    private ArrayList<Button> materials;
    private ArrayList<Button> players_buttons;

    public ArrayList<Button> GetMaterials()
    {
        if(materials==null)
        {
            materials=new ArrayList<>();
            Arrays.stream(Material.values()).forEach(material ->
            {
                Button button = new Button(material,material.toString());
                button.setObjectHolder(material);
                materials.add(button);
            });
        }
        return materials;
    }

    public ArrayList<Button> GetPlayers()
    {
        if(players_buttons == null)
        {
            players_buttons=new ArrayList<>();
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
    private ListGUI Get(Player player)
    {
        ListGUI gui_list= null;
        if(players.containsKey(player.getUniqueId())==false)
        {
            gui_list =new ListGUI(null,"list"+player.getName(),6);
            gui_list.SelectiveMode();
            players.put(player.getUniqueId(),gui_list);
        }
        else
        {
            gui_list = players.get(player.getUniqueId());
        }
        return gui_list;
    }


    public static ListGUI Get(Player player, String title, ArrayList<Button> items, InventoryEvent acction)
    {
        ListGUI gui_list = Instnace().Get(player);
        gui_list.SetName(title);
        gui_list.SelectItem(acction);
        gui_list.ClearItems();
        gui_list.AddButtons(items);

        return gui_list;
    }
    public static ListGUI Get(Player player, SearchType searchType , InventoryEvent acction)
    {
        ListGUI gui_list = Instnace().Get(player);
        ArrayList<Button> buttons = new ArrayList<>();
        switch (searchType)
        {
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
                          Material material =  e.GetHoldingObject();
                          if(material.isBlock())
                            return true;
                          else
                            return false;
                        } ).collect(Collectors.toList()));

                break;

            case Players:

                gui_list.SetName("Select player");
                gui_list.ClearItems();
                gui_list.AddButtons(Instnace().GetPlayers());
                break;
            case PlayerInventory:

                PlayerInventory inventory = player.getInventory();
                Button button;
                for(int i=0;i<90;i++)
                {
                    if(inventory.getItem(i) != null)
                    {
                        button= Button.getItemStack(inventory.getItem(i));
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


}