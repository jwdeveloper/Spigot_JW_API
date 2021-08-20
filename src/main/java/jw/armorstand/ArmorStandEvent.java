package jw.armorstand;

import jw.events.EventBase;
import jw.gui.button.Button;
import jw.gui.button.ButtonBuilder;
import jw.gui.button.ButtonFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.HashMap;
import java.util.Optional;

public class ArmorStandEvent extends EventBase
{
    Button x;
    Button y;
    Button z;
    Button cancel;
    Direction direction = Direction.x;
    private HashMap<Player,ArmorStand> armorStandEventHashMap = new HashMap<>();
    float speed = 0.4f;

    public enum Direction
    {
        x,y,z
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
        {
         if(!armorStandEventHashMap.containsKey(event.getPlayer()))
             return;


         Player player = event.getPlayer();
         int slot = event.getPlayer().getInventory().getHeldItemSlot();

         switch (slot)
         {
             case  0 ->
             {
                 x.getOnClick().Execute(player,x);
             }
             case  1 ->
                     {
                         y.getOnClick().Execute(player,y);
                     }
             case  2 ->
                     {
                         z.getOnClick().Execute(player,z);
                     }
             case  3 ->
                     {
                         cancel.getOnClick().Execute(player,cancel);
                     }
         }

        }
    }

    @EventHandler
    public void onChangeSlotEvent(PlayerItemHeldEvent event)
    {
        if(!armorStandEventHashMap.containsKey(event.getPlayer()))
        {
            return;
        }
        int direct =  event.getNewSlot()>event.getPreviousSlot()?1:-1;
        ArmorStand armorStand = armorStandEventHashMap.get(event.getPlayer());
        switch (direction)
        {
            case x ->
                    {
                        armorStand.teleport(armorStand.getLocation().add(direct*speed,0,0));
                    }
            case y ->
                    {
                        armorStand.teleport(armorStand.getLocation().add(0,direct*speed,0));
                    }
            case z ->
                    {
                        armorStand.teleport(armorStand.getLocation().add(0,0,direct*speed));
                    }
        }
    }
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event)
    {
        if(armorStandEventHashMap.containsKey(event.getPlayer()))
        {
            event.setCancelled(true);
        }
    }
    public void Open(Player player, ArmorStand armorStandEvent)
    {
        if(armorStandEventHashMap.containsKey(player))
            return;

        this.armorStandEventHashMap.put(player,armorStandEvent);
        createbuttons(player);
    }

    public void createbuttons(Player player)
    {
        x = new ButtonBuilder().setMaterial(Material.GREEN_DYE)
                .setOnClick((player1, button) ->
                {
                    player1.sendMessage("Set X");
                    this.direction = Direction.x;
                })
                .build();
        y = new ButtonBuilder().setMaterial(Material.GREEN_DYE)
                .setOnClick((player1, button) ->
                {
                    player1.sendMessage("Set y");
                    this.direction = Direction.y;
                })
                .build();
        z = new ButtonBuilder().setMaterial(Material.GREEN_DYE)
                .setOnClick((player1, button) ->
                {
                    player1.sendMessage("Set z");
                    this.direction = Direction.z;
                })
                .build();
        cancel = ButtonFactory.cancelButton();
        cancel.setOnClick((player1, button) ->
        {
            this.cancel(player);
        });
        player.getInventory().setItem(0,x);
        player.getInventory().setItem(1,y);
        player.getInventory().setItem(2,z);
        player.getInventory().setItem(3,cancel);
    }

    public void cancel(Player player)
    {
        player.getInventory().setItem(0,null);
        player.getInventory().setItem(1,null);
        player.getInventory().setItem(2,null);
        player.getInventory().setItem(3,null);
        this.armorStandEventHashMap.remove(player);
    }
}
