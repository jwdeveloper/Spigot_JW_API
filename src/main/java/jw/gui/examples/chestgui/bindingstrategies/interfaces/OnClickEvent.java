package jw.gui.examples.chestgui.bindingstrategies.interfaces;

import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.gui.button.Button;
import org.bukkit.entity.Player;

public interface OnClickEvent<T>
{
    public  void OnClick(Player player, Button button, BindingStrategy<T> bindingStrategy, T currentValue);
}
