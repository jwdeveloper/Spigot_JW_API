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
    public ButtonBuilderChestGUI self() {
        return this;
    }


    public ButtonBuilderChestGUI bindField(BindingStrategy bindingStrTest)
    {
        bindingStrTest.setChestGUI(chestGUI);
        bindingStrTest.setButton(button);
      return self();
    }

    public ButtonBuilderChestGUI bindField(BindingField bindingField)
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
        return bindingStrategy == null? self():bindField(bindingStrategy);
    }

    public ButtonBuilderChestGUI setAnimationFrames(Material ... frames)
    {

        return self();
    }


    public Button buildAndAdd()
    {
        chestGUI.addButton(this.button,this.button.getHeight(),this.button.getWidth());
        return button;
    }


}
