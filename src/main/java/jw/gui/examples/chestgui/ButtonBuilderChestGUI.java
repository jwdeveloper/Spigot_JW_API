package jw.gui.examples.chestgui;

import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.*;
import jw.gui.examples.chestgui.bindingstrategies.examples.BoolenBindStrategy;
import jw.gui.examples.chestgui.bindingstrategies.examples.MaterialBindStrategy;
import jw.gui.examples.chestgui.bindingstrategies.examples.NumberBindStrategy;
import jw.gui.examples.chestgui.bindingstrategies.examples.TextBindStrategy;
import jw.gui.button.Button;
import jw.gui.button.ButtonBuilder;
import jw.utilites.binding.BindingField;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

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


    public ButtonBuilderChestGUI BindField(BindingStrategy bindingStrTest)
    {
        bindingStrTest.setChestGUI(chestGUI);
        bindingStrTest.setButton(button);
      return Self();
    }

    public ButtonBuilderChestGUI BindField(BindingField bindingField)
    {
        if(!bindingField.isBinded())
        {
            Bukkit.getConsoleSender()
                    .sendMessage(ChatColor.DARK_RED+"Field "+
                                    ChatColor.WHITE+bindingField.getClass()+
                                    ChatColor.DARK_RED+" is not binded");
        }

        BindingStrategy bindingStrategy = null;
        switch (bindingField.getType().getName())
        {
            case "java.lang.String":
                bindingStrategy  = new TextBindStrategy(bindingField);
                break;
            case "org.bukkit.Material":
                bindingStrategy  = new MaterialBindStrategy(bindingField);
                break;
            case "int":
            case "float":
            case "double":
            case "java.lang.Number":
            case "java.lang.Integer":
                bindingStrategy  = new NumberBindStrategy(bindingField);
                break;
            case "boolean":
            case "java.lang.Boolean":
                bindingStrategy  = new BoolenBindStrategy(bindingField);
                break;
            default:
                Bukkit.getConsoleSender()
                        .sendMessage(ChatColor.DARK_RED+"Field type not supported for binding "+
                                        ChatColor.WHITE+bindingField.getType().getTypeName());
                break;
        }
        return bindingStrategy == null? Self():BindField(bindingStrategy);
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
