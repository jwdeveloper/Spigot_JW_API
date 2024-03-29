package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import jw.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NumberBindStrategy extends BindingStrategy<Number> {


    public NumberBindStrategy(BindingField<Number> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button,BindingStrategy<Number> bindingStrategy, Number currentValue) {

    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Number newValue)
    {
          button.setDescription(ChatColor.DARK_GREEN+this.getFieldName()+": "+this.getValue());
    }
}