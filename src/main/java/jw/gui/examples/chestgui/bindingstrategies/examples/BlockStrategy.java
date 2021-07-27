package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.SelectListGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import jw.utilites.Emoticons;
import jw.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockStrategy extends BindingStrategy<Material>
{
    public BlockStrategy(BindingField<Material> bindingField) {
        super(bindingField);
    }

    @Override
    public void onClick(Player player, Button button,BindingStrategy<Material> bindingStrategy, Material currentValue)
    {
        SelectListGUI.get(player, SelectListGUI.SearchType.Block, (player1, button1) ->
        {
            setValue(button1.getHoldingObject());
            this.chestGUI.open(player1);
        }).setParent(chestGUI).open(player);
    }

    @Override
    public void onValueChanged(ChestGUI inventoryGUI, Button button, Material newValue)
    {
        button.setMaterial(newValue);
        button.addDescription(ChatColor.WHITE+ Emoticons.arrowright+" "+newValue.name());
    }

}