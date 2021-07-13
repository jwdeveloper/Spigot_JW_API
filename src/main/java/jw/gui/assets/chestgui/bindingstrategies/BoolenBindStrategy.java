package jw.gui.assets.chestgui.bindingstrategies;

import jw.gui.assets.ChestGUI;
import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BoolenBindStrategy extends BindingStrategy<Boolean> {





    @Override
    public void OnClick(Player player, Button button, Boolean currentValue) {
        boolean value = GetValue();
        SetValue(!value);
    }
    @Override
    public void OnValueChanged(ChestGUI inventoryGUI, Button button, Boolean newValue) {

        String description = newValue ?ChatColor.GREEN+""+newValue: ChatColor.RED+""+newValue;
        description = ChatColor.WHITE +" "+ChatColor.BOLD+""+" "+description;
        button.SetHighlighted(newValue);
        button.SetDescription(description);
    }


}
