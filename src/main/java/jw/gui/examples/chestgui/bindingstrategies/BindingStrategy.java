package jw.gui.examples.chestgui.bindingstrategies;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.interfaces.OnChangeEvent;
import jw.gui.examples.chestgui.bindingstrategies.interfaces.OnClickEvent;
import jw.gui.button.Button;
import jw.gui.events.ButtonEvent;
import jw.utilites.binding.BindingField;
import org.bukkit.entity.Player;

public class BindingStrategy<T> implements ButtonEvent {

    protected ChestGUI chestGUI;
    protected Button button;
    protected BindingField<T> bindingField;
    public OnChangeEvent<T> onChangeEvent = this::OnValueChanged;
    public OnClickEvent<T> onClickEvent = this::OnClick;

    public BindingStrategy(Button button,ChestGUI chestGUI,BindingField<T> bindingField)
    {
      setBindingField(bindingField);
      setButton(button);
      setChestGUI(chestGUI);
    }
    public BindingStrategy(BindingField<T> bindingField)
    {
        setBindingField(bindingField);
    }
    public BindingStrategy()
    {

    }
    @Override
    public void Execute(Player player, Button button)
    {
        if(!bindingField.isBinded())
             return;
        onClickEvent.OnClick(player, button,this, getValue());
    }
    public void OnChange(T newValue)
    {
        if(!bindingField.isBinded() || !chestGUI.IsOpen())
            return;

        onChangeEvent.OnValueChanged(chestGUI, button, newValue);
        chestGUI.RefreshButton(button);
    }

    protected void OnClick(Player player, Button button,BindingStrategy<T> bindingStrategy, T currentValue)
    {
    }
    protected void OnValueChanged(ChestGUI inventoryGUI, Button button, T newValue)
    {
    }

    public void setOnChangeEvent(OnChangeEvent onChangeEvent)
    {
        this.onChangeEvent = onChangeEvent;
    }
    public void setOnClickEvent(OnClickEvent onClickEvent)
    {
        this.onClickEvent = onClickEvent;
    }
    public void setButton(Button button)
    {
        this.button = button;
    }
    public void setChestGUI(ChestGUI chestGUI)
    {
        this.chestGUI = chestGUI;
        chestGUI.AddBindStrategy(this);
    }
    public void setBindingField(BindingField<T> bindingField)
    {
        this.bindingField = bindingField;
        this.bindingField.onChange(this::OnChange);
    }
    public  T getValue()
    {
        return  bindingField.get();
    }
    public void setValue(T newValue)
    {
        bindingField.set(newValue);
    }
    public Button getButton()
    {
        return button;
    }
    public String getFieldName(){ return  bindingField.getFieldName();}
    public void setObject(T object)
    {
        this.bindingField.setObject(object);
    }

}
