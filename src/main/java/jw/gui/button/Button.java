package jw.gui.button;

import jw.gui.events.ButtonEvent;
import jw.packets.ObjectBinder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Button extends ItemStack
{
    private Vector position = new Vector(-1,-1,0);
    private boolean isActive = true;
    private String permission  = "";
    private ButtonActionsEnum action = ButtonActionsEnum.EMPTY;
    private Object objectHolder;
    private String tag = "";
    private Sound sound;
    private ButtonEvent onClick = (player,button)->{};
    private boolean highlighted = false;


    private final ObjectBinder objectBinder;

    public Button(Material material)
    {
        super(material);
        this.HideAtributes();
        this.objectBinder = new ObjectBinder();
    }
    public Button(Material material, ButtonActionsEnum action)
    {
        this(material);
        this.action =action;
    }
    public Button(Material material, String title)
    {
        this(material,ButtonActionsEnum.CLICK);
        this.SetName(title);
    }
    public Button(Material material, String title, ButtonActionsEnum action)
    {
        this(material,title);
        this.action =action;
    }
    public Button(Material material, String title, String description)
    {
        this(material,title);
        this.SetDescription(description);
    }
    public Button(Material material, String name, ButtonEvent onClick)
    {
        this(material,name);
        this.onClick = onClick;
    }
    public Button(Material material, String name, int height, int width,  ButtonEvent onClick)
    {
        this(material,name,onClick);
        SetPosition(height,width);
    }

    public void DisplayBindedValue()
    {
            if(Material.class ==  objectBinder.getClass_())
            {
                Material value =(Material)objectBinder.getFieldValue();
                this.setType(value);
            }
            if(boolean.class == objectBinder.getClass_())
            {
                boolean value =(boolean)objectBinder.getFieldValue();
                if(value)
                {
                    SetDescription(ChatColor.GREEN+"[" +value + "]");
                }
                else
                {
                    SetDescription(ChatColor.RED+"[" +value + "]");
                }
                SetHighlighted(value);
            }
            else
            {
                SetDescription(objectBinder.getFieldValue().toString());
            }
    }

    public void HideAtributes()
    {
        ItemMeta meta = this.getItemMeta();
        if(meta ==null)
            return;
        Arrays.asList(ItemFlag.values()).forEach(i -> meta.addItemFlags(i));
        this.setItemMeta(meta);
    }

    public boolean CanPlayerUse(Player player)
    {
        if( this.permission!=null && !player.hasPermission(permission))
        {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
            return false;
        }
        return true;
    }
    public void SetHighlighted(boolean value)
    {
        ItemMeta meta = this.getItemMeta();
        highlighted = value;
        if(value)
            meta.addEnchant(Enchantment.ARROW_FIRE, 10, true);
        else
            meta.removeEnchant(Enchantment.ARROW_FIRE);
        this.setItemMeta(meta);
    }
    public void SetCustomModelData(int id)
    {
        ItemMeta meta = this.getItemMeta();
        meta.setCustomModelData(id);
        this.setItemMeta(meta);
    }
    public void SetName(String name)
    {
        ItemMeta meta = this.getItemMeta();
        if(meta ==null)
            return;

        meta.setDisplayName(name.replace("&","§"));
        this.setItemMeta(meta);
    }
    public void AddDescription(String ... description)
    {
        if(description == null)
            return;
        ItemMeta meta = this.getItemMeta();
        if(meta ==null)
            return;
        if(meta.getLore()!=null)
        {
            for (String s : description) {
                meta.getLore().add(s);
            }
            meta.setLore(meta.getLore());
        }
        else
        {
            meta.setLore(Arrays.asList(description));
        }
        this.setItemMeta(meta);
    }
    public Material getMaterial()
    {
       return this.getType();
    }
    public void setMaterial(Material material)
    {
        this.setType(material);
    }

    public boolean isBinded()
    {
     return   this.objectBinder.hasObject() && this.objectBinder.hasField();
    }

    public void displayHoldedObject()
    {
        if(objectHolder == null)
            return;

        Class _class = objectHolder.getClass();
        List<String> description = new ArrayList<String>();

        description.add("Object name: "+objectHolder);
        for(Field field : _class.getFields())
        {
            try
            {
                description.add(field.getName()+": "+field.get(objectHolder));
            }
            catch (Exception e)
            {
                description.add(ChatColor.WHITE+field.getName()+": "+ChatColor.DARK_RED+"Can not get value");
            }

        }


        this.SetDescription(description);
    }

    public void SetDescription(String ... description)
    {
       this.SetDescription(Arrays.asList(description));
    }
    public void SetDescription(List<String> description)
    {
        if(description == null)
            return;
        ItemMeta meta = this.getItemMeta();

        if(meta ==null)
            return;

        meta.setLore(description);
        this.setItemMeta(meta);
    }
    public <T> T GetHoldingObject()
    {
        return (T)this.objectHolder;
    }
    public void SetPermission(String permission)
    {
        this.permission = permission;
    }
    public void SetPosition(int height,int width)
    {
        this.position = new Vector(width,height,0);
    }
    public void SetHeight(int height)
    {
        this.position.setY(height);
    }
    public void SetWidth(int width)
    {
        this.position.setX(width);
    }
    public int GetHeight()
    {
        return position.getBlockY();
    }
    public int GetWidth()
    {
        return position.getBlockX();
    }
    public void SetActive(boolean isActive)
    {
        this.isActive = isActive;
    }
    public  boolean IsActive()
    {
        return  isActive;
    }
    public void setAction(ButtonActionsEnum action)
    {
        this.action = action;
    }
    public ButtonActionsEnum GetAction()
    {
        return this.action;
    }
    public ObjectBinder getObjectBinder() {
        return objectBinder;
    }
    public void setObjectHolder(Object objectHolder) {
        this.objectHolder = objectHolder;
    }
    public boolean isHighlighted()
    {
        return this.highlighted;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setActive(boolean isActive)
    {
        this.isActive =isActive;
    }


    public ButtonEvent getOnClick() {
        return onClick;
    }

    public void setOnClick(ButtonEvent onClick)
    {
        this.onClick = onClick;
    }

    public Sound getSound()
    {
        return this.sound;
    }
    public boolean hasSound()
    {
        return this.sound != null;
    }
    public void setSound(Sound sound)
    {
        this.sound =sound;
    }

    public boolean checkPermission(Player player)
    {
       return player.hasPermission(permission);
    }

    public static Button getItemStack(ItemStack itemStack) {
        Button Button = new Button(itemStack.getType(), itemStack.getType().name());
        Button.setData(itemStack.getData());
        Button.setItemMeta(itemStack.getItemMeta());
        Button.setAmount(itemStack.getAmount());
        return Button;
    }

    public static ItemStack toItemStack(Button Button) {
        ItemStack itemStack = new ItemStack(Button.getType(), Button.getAmount());
        itemStack.setAmount(Button.getAmount());
        itemStack.setData(Button.getData());
        itemStack.setItemMeta(Button.getItemMeta());
        return itemStack;
    }
}