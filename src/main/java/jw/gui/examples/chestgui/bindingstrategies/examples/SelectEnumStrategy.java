package jw.gui.examples.chestgui.bindingstrategies.examples;

import jw.gui.button.Button;
import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.utilites.Emoticons;
import jw.utilites.binding.BindingField;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SelectEnumStrategy<T extends Enum<T>> extends BindingStrategy<T> {
    private T[] enumValues;
    private int index = 0;

    public SelectEnumStrategy(BindingField<T> bindingField, Class<T> tEnum) {
        super(bindingField);
        enumValues = tEnum.getEnumConstants();
    }

    @Override
    protected void onClick(Player player, Button button, BindingStrategy<T> bindingStrategy, T currentIndex) {
        index = (index + 1) % enumValues.length;
        setValue(enumValues[index]);
    }

    @Override
    protected void onValueChanged(ChestGUI inventoryGUI, Button button, T newIndex) {
        String[] description = new String[enumValues.length];
        for (int i = 0; i < enumValues.length; i++) {
            if (i == index) {
                description[i] = ChatColor.WHITE + "" + ChatColor.BOLD + Emoticons.arrowright + " " + enumValues[i].name();
            } else {
                description[i] = ChatColor.GRAY + "- " + enumValues[i].name();
            }
        }
        button.setDescription(description);
    }
}