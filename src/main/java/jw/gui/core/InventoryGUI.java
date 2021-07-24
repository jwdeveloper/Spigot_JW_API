package jw.gui.core;

import jw.gui.button.Button;
import jw.gui.button.ButtonBuilder;
import jw.gui.events.ButtonEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public abstract class InventoryGUI {
    protected Button[] buttons;
    protected String name;
    protected Inventory inventory;
    protected InventoryType inventoryType;
    protected int size = 1;
    protected int height = 1;
    protected Player player;
    protected boolean isTitleSet = false;
    protected String displayedName;
    private boolean enableLogs = false;
    private boolean isOpen = false;
    private InventoryGUI parent;
    protected static final int MAX_TITLE_SIZE = 38;

    protected abstract void onClick(Player player, Button button);
    protected abstract void onRefresh(Player player);
    protected abstract void onOpen(Player player);
    protected abstract void onClose(Player player);
    protected abstract void doClick(Player player, int index, ItemStack itemStack);

    protected InventoryGUI(String name, int height, InventoryType type) {
        this.height = Math.min(height, 6);
        this.name = name;
        this.size = height * 9;
        this.buttons = new Button[this.size];
        this.inventoryType = type;
        this.displayedName = name;
    }

    protected InventoryGUI(InventoryGUI parent, String name, int height, InventoryType type) {
        this(name, height, type);
        this.parent = parent;
    }

    private void createInventory() {
        switch (inventoryType) {
            case CHEST:
                this.inventory = Bukkit.createInventory(player, size, this.displayedName);
                break;
            case MERCHANT:
                //  this.inventory= player.openMerchant(Bukkit.createMerchant(this.displayedName),true).getTopInventory();
                break;
            case ANVIL:
                this.inventory = Bukkit.createInventory(player, InventoryType.ANVIL, this.displayedName);
                this.size = InventoryType.ANVIL.getDefaultSize();
                break;
            default:
                this.inventory = Bukkit.createInventory(player, this.inventoryType, this.displayedName);
                break;
        }
    }
    public void open(Player player) {
        if (parent != null) {
            parent.close();
        }

        this.player = player;

        if (player != null && this.player.isOnline()) {
            this.onOpen(this.player);
            createInventory();
            refreshButtons();
            InventoryGUIEventsHander.Instnace().register(this);
            player.openInventory(this.inventory);
            isOpen = true;
        }
        if (this.enableLogs)
            this.displayLog("Open", ChatColor.GREEN);
    }

    public void refresh() {
        if (player != null && this.player.isOnline() && isOpen) {
            refreshButtons();
            this.onRefresh(this.player);
        }
        if (this.enableLogs)
            this.displayLog("Refresh", ChatColor.YELLOW);
    }

    public void close() {
        InventoryGUIEventsHander.Instnace().unregister(this);
        isOpen = false;
        if (player != null && this.player.isOnline()) {
            this.onClose(this.player);
            player.closeInventory();
        }
        if (this.enableLogs)
            this.displayLog("Close", ChatColor.RED);
    }

    public void setName(String name) {
        this.displayedName = name;
        if (player != null && player.isOnline() && isOpen) {
            InventoryGUIEventsHander.Instnace().unregister(this);
            createInventory();
            refreshButtons();
            player.openInventory(this.inventory);
            InventoryGUIEventsHander.Instnace().register(this);
        }
    }

    public void setTitle(String title) {
        isTitleSet = true;
        StringBuilder result = new StringBuilder();
        title = "§3§l" + title;
        int title_size = title.length();
        int start = (MAX_TITLE_SIZE / 2) - (title_size / 2);
        int l = 0;
        for (int i = 0; i < start + title_size; i++) {
            if (i >= start) {
                result.append(title.charAt(l));
                l += 1;
            } else
                result.append(" ");
        }
        this.setName(result.toString());
    }

    protected void refreshButtons() {
        Button button = null;
        for (int i = 0; i < buttons.length; i++)
        {
            button = buttons[i];
            if(button != null && button.IsActive())
            {
                this.inventory.setItem(i,button);
            }
            else
            {
                this.inventory.setItem(i,null);
            }
        }
    }

    public void refreshButton(Button button)
    {
        int index = getButtonIndex(button);
        if(button.IsActive())
        {
            this.inventory.setItem(index, buttons[index]);
        }
        else
        {
            this.inventory.setItem(index,null);
        }


    }

    public void setActive(boolean isActive) {
        isOpen = isActive;
        if (isOpen)
            InventoryGUIEventsHander.Instnace().register(this);
        else
            InventoryGUIEventsHander.Instnace().unregister(this);
    }

    public InventoryGUI setParent(InventoryGUI parent) {
        this.parent = parent;
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public InventoryGUI getParent() {
        return this.parent;
    }
    public int getButtonIndex(Button button)
    {
        return button.getHeight() * 9 + button.getWidth() % 9;
    }
    public Button getButton(int height, int width) {
        int pos = height * 9 + width % 9;
        return buttons[pos] == null ? null : buttons[pos];
    }
    public Button getButton(int index) {
        int position = index >= buttons.length ? buttons.length - 1 : index;
        return buttons[position] == null ? new Button(Material.DIRT) : buttons[position];
    }
    public ButtonBuilder buildButton()
    {
        return new ButtonBuilder();
    }

    public void addButton(Button button) {
        buttons[button.getHeight() * 9 + button.getWidth() % 9] = button;
    }

    public void addButton(Button button, int index) {
        if (index <=this.size)
            buttons[index] = button;
    }

    public void addButton(Button button, int height, int width) {
        int pos = height * 9 + width % 9;
        if(pos >=0 && pos<this.size)
         buttons[pos] = button;
    }


    public void addButton(Material material, String name, String description, int height, int width, ButtonEvent onClick) {
        Button gui_button = new Button(material, name, height, width, onClick);
        gui_button.setDescription(description);
        addButton(gui_button);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public boolean isOpen() {
        return this.isOpen;
    }

    public void enableLogs(boolean enableLogs) {
        this.enableLogs = enableLogs;
    }

    public boolean isEnableLogs() {
         return this.enableLogs;
    }

    public void displayLog(String message, ChatColor chatColor) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + this.toString() + ": " + chatColor + message);
    }

    public void playSound(Sound sound)
    {
        player.playSound(player.getLocation(),sound,1,1);
    }

    public void clearButtons()
    {
        Arrays.fill(buttons, null);
        this.refresh();
    }

    public int getSize()
    {
        return this.size;
    }

    public boolean isSlotEmpty(int index)
    {
        return buttons[index] == null;
    }
}
