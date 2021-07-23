package jw.gui.examples.chestgui.bindingstrategies.interfaces;

import jw.gui.examples.ChestGUI;
import jw.gui.button.Button;

public interface OnChangeEvent<T>
{
    public  void OnValueChanged(ChestGUI inventoryGUI, Button button, T newValue);
}
