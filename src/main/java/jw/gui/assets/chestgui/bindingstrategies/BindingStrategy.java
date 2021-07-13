package jw.gui.assets.chestgui.bindingstrategies;

import jw.gui.assets.ChestGUI;
import jw.gui.button.Button;
import jw.gui.events.ButtonEvent;
import jw.packets.ObjectChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class BindingStrategy<T> implements ButtonEvent, ObjectChangeListener<T> {

    protected ChestGUI chestGUI;
    protected Button button;

    public BindingStrategy() {

    }

    public BindingStrategy(ChestGUI chestGUI, Button button) {
        setGUI(chestGUI);
        setButton(button);
    }

    public abstract void OnClick(Player player, Button button, T currentValue);

    public abstract void OnValueChanged(ChestGUI inventoryGUI, Button button, T newValue);

    public void setGUI(ChestGUI chestGUI) {
        this.chestGUI = chestGUI;
    }

    public void setButton(Button button) {
        this.button = button;
        this.button.getObjectBinder().setChangeListener(this);
    }

    public <T> T GetValue()
    {
       return   this.button.getObjectBinder().getFieldValue();
    }

    public <T> void SetValue(T newvalue)
    {
       this.button.getObjectBinder().setFieldValue(newvalue);
    }

    @Override
    public void Execute(Player player, Button button)
    {
        if(!button.getObjectBinder().hasField())
             return;

        OnClick(player, button, GetValue());
    }

    @Override
    public void OnChange(Object object, T newValue)
    {
        if(!button.getObjectBinder().hasField())
            return;

        OnValueChanged(chestGUI, button, newValue);
        chestGUI.RefreshButton(button);
    }


}
