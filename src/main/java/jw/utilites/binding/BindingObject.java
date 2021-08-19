package jw.utilites.binding;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class BindingObject<T> extends BindingField<T> {


    private String objectName;

    public BindingObject(String name) {
        super(name, new Object());
    }
    public BindingObject() {
        this("Object");
    }
    @Override
    protected boolean bind(String filed, Object classObject) {
        this.objectName = filed;
        this.object = classObject;
        return true;
    }

    @Override
    public void set(T value) {
        this.object = value;
        for (Consumer<T> event : onChange) {
            event.accept(value);
        }
    }

    @Override
    public T get() {
        return (T) this.object;
    }


    @Override
    public void setObject(Object object) {

    }
}
