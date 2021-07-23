package jw.utilites.binding;

import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BindingField<T> implements Bindable<T> {

    private Field field;
    private Object object;
    private Class _class;
    private Class fieldType;
    private boolean isBinded;

    private List<Consumer<T>> onChange = new ArrayList<>();

    public BindingField(String filed, Object classObject) {
        isBinded = bind(filed, classObject);
    }

    public Class getType() {
        return fieldType;
    }

    public boolean isBinded() {
        return isBinded;
    }

    public void onChange(Consumer<T> onChangeEvent) {
        this.onChange.add(onChangeEvent);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return field != null ? field.getName() : "";
    }

    @Override
    public void set(T value) {
        if (!isBinded)
            return;
        try {
            field.set(object, value);
            for (Consumer<T> consumer : onChange) {
                consumer.accept(value);
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " Error set binding field: " + e.getMessage());
        }
    }

    public void setAsync(T value) {
        if (!isBinded)
            return;
        Bukkit.getScheduler().runTask(InicializerAPI.GetPlugin(), () ->
        {
            try {
                field.set(object, value);

                for (Consumer<T> consumer : onChange) {
                    consumer.accept(value);
                }


            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " Error set binding field: " + e.getMessage());
            }
        });
    }

    @Override
    public T get() {
        if (!isBinded)
            return null;
        try {
            return (T) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean bind(String filed, Object classObject) {
        try {
            this.field = classObject.getClass().getField(filed);
            this.object = classObject;
            this._class = classObject.getClass();
            this.fieldType = field.getType();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
