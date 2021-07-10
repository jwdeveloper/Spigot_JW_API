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
    protected boolean enableLogs = false;
    protected boolean isOpen = false;
    protected InventoryGUI parent;
    protected static final int max_title_size = 38;

    public Consumer<String> onError = (a) -> {
    };

    public abstract void OnClick(Player player, Button button);

    public abstract void OnRefresh(Player player);

    public abstract void OnOpen(Player player);

    public abstract void OnClose(Player player);

    protected abstract void DoClick(Player player, int index, ItemStack itemStack);

    ArrayList<Integer> observedTasks = new ArrayList<>();

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

    private void CreateInventory() {
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

    public void Add_task(int id) {
        if (!observedTasks.contains(id))
            observedTasks.add(id);
    }

    public void Stop_task(int id) {
        if (observedTasks.contains(id))
            Bukkit.getScheduler().cancelTask(id);
    }

    public void Stop_tasks() {
        for (Integer observedTask : observedTasks) {
            Bukkit.getScheduler().cancelTask(observedTask);
        }
    }

    public void Open(Player player) {
        if (parent != null) {
            parent.Close();
        }

        this.player = player;

        if (player != null && this.player.isOnline()) {
            this.OnOpen(this.player);
            CreateInventory();
            RefreshButtons();
            InventoryGUIEventsHander.Instnace().Register(this);
            player.openInventory(this.inventory);
            isOpen = true;
        }
        if (this.enableLogs)
            this.DisplayLog("Open", ChatColor.GREEN);
    }

    public void Refresh() {
        if (player != null && this.player.isOnline() && isOpen) {
            RefreshButtons();
            this.OnRefresh(this.player);
        }
        if (this.enableLogs)
            this.DisplayLog("Refresh", ChatColor.YELLOW);
    }

    public void Close() {
        InventoryGUIEventsHander.Instnace().Unregister(this);
        isOpen = false;
        if (player != null && this.player.isOnline()) {
            this.OnClose(this.player);
            //  this.onClose.forEach(e -> e.Execute(player,null));
            player.closeInventory();
        }
        if (this.enableLogs)
            this.DisplayLog("Close", ChatColor.RED);
    }

    public void SetName(String name) {
        this.displayedName = name;
        if (player != null && player.isOnline() && isOpen) {
            InventoryGUIEventsHander.Instnace().Unregister(this);
            CreateInventory();
            RefreshButtons();
            player.openInventory(this.inventory);
            InventoryGUIEventsHander.Instnace().Register(this);
        }
    }

    public void SetTitle(String title) {
        isTitleSet = true;
        StringBuilder result = new StringBuilder();
        title = "§3§l" + title;
        int title_size = title.length();
        int start = (max_title_size / 2) - (title_size / 2);
        int l = 0;
        for (int i = 0; i < start + title_size; i++) {
            if (i >= start) {
                result.append(title.charAt(l));
                l += 1;
            } else
                result.append(" ");
        }
        this.SetName(result.toString());
    }

    protected void RefreshButtons() {
        for (int i = 0; i < buttons.length; i++)
                this.inventory.setItem(i, buttons[i]);
    }

    public void SetActive(boolean isActive) {
        isOpen = isActive;
        if (isOpen)
            InventoryGUIEventsHander.Instnace().Register(this);
        else
            InventoryGUIEventsHander.Instnace().Unregister(this);
    }

    public InventoryGUI SetParent(InventoryGUI parent) {
        this.parent = parent;
        return this;
    }

    public Player GetPlayer() {
        return this.player;
    }

    public InventoryGUI GetParent() {
        return this.parent;
    }

    public Button GetButton(int height, int width) {
        int pos = height * 9 + width % 9;
        return buttons[pos] == null ? null : buttons[pos];
    }

    public Button GetButton(int index) {
        int position = index >= buttons.length ? buttons.length - 1 : index;
        return buttons[position] == null ? new Button(Material.DIRT) : buttons[position];
    }
    public ButtonBuilder BuildButton()
    {
        return new ButtonBuilder();
    }

    public void AddButton(Button button) {
        buttons[button.GetHeight() * 9 + button.GetWidth() % 9] = button;
    }

    public void AddButton(Button button, int index) {
        if (index <=this.size)
            buttons[index] = button;
    }

    public void AddButton(Button button, int height, int width) {
        int pos = height * 9 + width % 9;
        if(pos >=0 && pos<this.size)
         buttons[pos] = button;
    }


    public void AddButton(Material material, String name, String description, int height, int width, ButtonEvent onClick) {
        Button gui_button = new Button(material, name, height, width, onClick);
        gui_button.SetDescription(description);
        AddButton(gui_button);
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }


    public boolean IsOpen() {
        return this.isOpen;
    }

    public void EnableLogs(boolean enableLogs) {
        this.enableLogs = enableLogs;
    }

    public boolean IsEnableLogs() {
         return this.enableLogs;
    }

    public void DisplayLog(String message, ChatColor chatColor) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + this.toString() + ": " + chatColor + message);
    }

    public void PlaySound(Sound sound)
    {
        player.playSound(player.getLocation(),sound,1,1);
    }

    public void ClearButtons()
    {
        for(int i=0;i<buttons.length;i++)
        {
            buttons[i]=null;
        }
        this.Refresh();
    }

    public int GetSize()
    {
        return this.size;
    }

    public boolean IsSlotEmpty(int index)
    {
        return buttons[index] == null;
    }
}
