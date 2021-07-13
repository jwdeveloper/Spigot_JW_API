package jw.gui.assets.chestgui;

import jw.gui.assets.ChestGUI;
import jw.gui.assets.chestgui.bindingstrategies.*;
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


    public ButtonBuilderChestGUI BindProperty(String fieldName ,BindingStrategy bindingStrategy)
    {
        Field field = ObjectHelper.FindProperty(fieldName, chestGUI.detail.getClass());
        if (field == null) {
            if (chestGUI.IsEnableLogs())
                Bukkit.getConsoleSender()
                        .sendMessage(ChatColor.DARK_RED + "Field " + ChatColor.WHITE + fieldName + ChatColor.DARK_RED + " not found while binding");
            return Self();
        }
        bindingStrategy.setButton(button);
        bindingStrategy.setGUI(chestGUI);

        button.getObjectBinder().setField(field);
        button.setOnClick(bindingStrategy);
        chestGUI.AddBindedButton(button);
        return Self();
    }


    public ButtonBuilderChestGUI BindField(String fieldName, BindingStrategy bindingStrTest)
    {
        Field field = ObjectHelper.FindProperty(fieldName, chestGUI.detail.getClass());
        if (field == null)
        {
            if(chestGUI.IsEnableLogs())
                Bukkit.getConsoleSender()
                        .sendMessage(ChatColor.DARK_RED+"Field "+ChatColor.WHITE+fieldName+ChatColor.DARK_RED+" not found while binding");
            return Self();
        }

        bindingStrTest.setGUI(chestGUI);
        bindingStrTest.setButton(button);
        button.getObjectBinder().setField(field);
        button.setOnClick(bindingStrTest);
        chestGUI.AddBindedButton(button);
      return Self();
    }

    public ButtonBuilderChestGUI BindField(String fieldName)
    {
        Field field = ObjectHelper.FindProperty(fieldName, chestGUI.detail.getClass());
        if (field == null)
        {
                Bukkit.getConsoleSender()
                      .sendMessage(ChatColor.DARK_RED+"Field "+ChatColor.WHITE+fieldName+ChatColor.DARK_RED+" not found while binding");
            return Self();
        }
        BindingStrategy bindingStrategy;
        switch (field.getType().getTypeName())
        {
            case "java.lang.String":
                bindingStrategy  = new TextBindStrategy();
                break;
            case "org.bukkit.Material":
                bindingStrategy  = new MaterialBindStrategy();
                break;
            case "int":
            case "float":
            case "double":
                bindingStrategy  = new NumberBindStrategy();
                break;
            case "boolean":
                bindingStrategy  = new BoolenBindStrategy();
                break;
            default:
                throw new IllegalStateException("Unexpected binding value: " + field.getType().getTypeName());
        }
        return BindField(fieldName,bindingStrategy);
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
