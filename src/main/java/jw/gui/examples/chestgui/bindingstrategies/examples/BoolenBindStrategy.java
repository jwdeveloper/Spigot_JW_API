package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import jw.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BoolenBindStrategy extends BindingStrategy<Boolean> {


    public BoolenBindStrategy(BindingField<Boolean> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button,BindingStrategy<Boolean>  bindingStrategy, Boolean currentValue) {
        boolean value = getValue();
        setValue(!value);
    }
    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Boolean newValue) {

        String description = newValue ?ChatColor.GREEN+""+newValue: ChatColor.RED+""+newValue;
        description = ChatColor.WHITE+""+ChatColor.BOLD+"Enabled: "+ChatColor.RESET+description;
        button.setHighlighted(newValue);
        button.setDescription(description);
    }


}
