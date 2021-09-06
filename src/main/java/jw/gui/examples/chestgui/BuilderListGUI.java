package jw.gui.examples.chestgui;

import jw.gui.button.ButtonActionsEnum;
import jw.gui.core.InventoryGUI;
import jw.gui.events.InventoryEvent;
import jw.gui.examples.chestgui.utilites.ButtonMapper;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class BuilderListGUI<T> {
    private ListGUI<T> chestGUI;
    private List<T> content = new ArrayList<>();
    private String title = "Select";
    private InventoryGUI parent;
    private Material material = Material.BLACK_STAINED_GLASS_PANE;
    private InventoryEvent onSelectEvent = (player, button) -> {};
    private int height = 6;
    private ButtonMapper<T> buttonMapper = (data, button) -> {
    };

    public BuilderListGUI<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public BuilderListGUI<T> setHeight(int height) {
        this.height = height;
        return this;
    }

    public BuilderListGUI<T> setButtonMapping(ButtonMapper<T> buttonMapper) {
        this.buttonMapper = buttonMapper;
        return this;
    }

    public BuilderListGUI<T> setBackGround(Material material) {
        this.material = material;
        return this;
    }

    public BuilderListGUI<T> setParent(InventoryGUI parent) {
        this.parent = parent;
        return this;
    }

    public BuilderListGUI<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    public BuilderListGUI<T> setOnSelect(InventoryEvent inventoryEvent) {
        this.onSelectEvent = inventoryEvent;
        return this;
    }

    public ListGUI<T> build()
    {
        chestGUI = new ListGUI<T>(parent, "Pick up", height) {
            @Override
            public void onInitialize() {
                this.getInsertButton().setActive(false);
                this.getDeleteButton().setActive(false);
                this.getSearchButton().setActive(false);
                this.getCopyButton().setActive(false);
                this.getCancelButton().setActive(false);
                this.currentAcction.set(ButtonActionsEnum.GET);
                this.onSelect = onSelectEvent;
                this.setTitle(title);
                this.setBackgroundMaterial(material);
                this.addButtons(content, buttonMapper);
            }
        };
        return chestGUI;
    }


}
