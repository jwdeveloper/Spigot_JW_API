package jw.utilites.files.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import jw.gui.examples.chestgui.bindingstrategies.BindingStrategy;
import jw.utilites.binding.BindingField;

public class BindingFieldSkip implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass)
    {
        return  BindingField.class.getName().equalsIgnoreCase(aClass.getName());
    }
}
