package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import jw.utilites.binding.BindingField;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TextBindStrategy  extends BindingStrategy<String> {


    public TextBindStrategy(BindingField<String> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button,BindingStrategy<String> bindingStrategy, String currentValue) {

    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, String newValue) {

    }
}