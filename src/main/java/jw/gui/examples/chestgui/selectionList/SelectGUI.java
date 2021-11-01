package jw.gui.examples.chestgui.selectionList;

import jw.gui.button.Button;
import jw.gui.button.ButtonActionsEnum;
import jw.gui.core.InventoryGUI;
import jw.gui.events.ButtonEvent;
import jw.gui.events.InventoryEvent;
import jw.gui.examples.chestgui.ListGUI;
import jw.gui.examples.chestgui.utilites.ButtonMapper;
import net.minecraft.world.item.ItemStack;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectGUI<T> extends ListGUI<T>
{
    public enum SearchType {
        PLAYERS, MATERIALS, INVENTORY, FILE, BLOCK, COLORS, FOOD
    }

    public SelectGUI(String name, int size)
    {
        super(null, name, size);
    }

    public SelectGUI setTitleName(String title) {
        super.setTitle(title);
        return this;
    }

    public SelectGUI onSelect(InventoryEvent event)
    {
        this.onSelect = event;
        return this;
    }
    @Override
    public void onOpen(Player player)
    {
        this.currentAcction.set(ButtonActionsEnum.GET);
        super.onOpen(player);
    }

    @Override
    public void onClose(Player player)
    {
        this.currentAcction.set(ButtonActionsEnum.EMPTY);
        super.onClose(player);
    }
}
