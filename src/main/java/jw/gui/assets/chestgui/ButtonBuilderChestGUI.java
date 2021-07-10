package jw.gui.assets.chestgui;

import jw.gui.assets.ChestGUI;
import jw.gui.button.Button;
import jw.gui.button.ButtonBuilder;
import jw.utilites.ObjectHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.lang.reflect.Field;

public class ButtonBuilderChestGUI extends ButtonBuilder<ButtonBuilderChestGUI>
{

    private ChestGUI chestGUI;

    public ButtonBuilderChestGUI(ChestGUI chestGUI)
    {
     this.chestGUI = chestGUI;
    }

    @Override
    public ButtonBuilderChestGUI Self() {
        return this;
    }

    public ButtonBuilderChestGUI SetBindedFiled(String fieldName)
    {
        Field field = ObjectHelper.FindProperty(fieldName, chestGUI.detail.getClass());
        if (field == null)
        {
            if(chestGUI.IsEnableLogs())
             Bukkit.getConsoleSender()
                   .sendMessage(ChatColor.DARK_RED+"Field "+ChatColor.WHITE+fieldName+ChatColor.DARK_RED+" not found while binding");
        }
        else
        {
            button.getObjectBinder().setField(field);
            chestGUI.AddBindedButton(button);
        }
        return Self();
    }

    public ButtonBuilderChestGUI SetAnimationFrames(Material ... frames)
    {

        return Self();
    }


    public Button BuildAndAdd()
    {
        chestGUI.AddButton(this.button,this.button.GetHeight(),this.button.GetWidth());
        return button;
    }


}
