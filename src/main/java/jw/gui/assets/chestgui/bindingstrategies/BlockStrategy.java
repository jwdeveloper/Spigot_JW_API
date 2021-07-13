package jw.gui.assets.chestgui.bindingstrategies;

import jw.gui.assets.ChestGUI;
import jw.gui.assets.chestgui.SelectListGUI;
import jw.gui.button.Button;
import jw.utilites.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockStrategy extends BindingStrategy<Material>
{
    @Override
    public void OnClick(Player player, Button button, Material currentValue)
    {
        SelectListGUI.Get(player, SelectListGUI.SearchType.Block, (player1, button1) ->
        {
            SetValue(button1.GetHoldingObject());
            this.chestGUI.Open(player1);
        }).SetParent(chestGUI).Open(player);
    }

    @Override
    public void OnValueChanged(ChestGUI inventoryGUI, Button button, Material newValue)
    {
        button.setMaterial(newValue);
        button.AddDescription(ChatColor.WHITE+ Emoticons.arrowright+" "+newValue.name());
    }

}