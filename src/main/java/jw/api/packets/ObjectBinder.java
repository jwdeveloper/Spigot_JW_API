package jw.api.packets;

import java.lang.reflect.Field;

public class ObjectBinder
{
    private Object object;
    private Field field;
    private Class class_;

    public ObjectBinder()
    {
        class_ = String.class;
        object = "";
    }


    public Object getObject() {
        return object;
    }

    public Object getFieldValue()
    {
        if(object!=null)
            try
            {
                return field.get(object);
            }
        catch (IllegalAccessError | IllegalAccessException ignored)
        {

        }
        return null;
    }

    public void setFieldValue(Object value)
    {
        if(object==null || field == null)
            return;
        try
        {
            field.setAccessible(true);
            field.set(object,value);
        }
        catch (IllegalAccessException ignored)
        {
        }
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Field getField() {
        return field;
    }

    public boolean hasObject()
    {
        return object!=null;
    }
    public boolean hasField()
    {
        return field!=null;
    }

    public void setField(Field field) {
        this.field = field;
        this.class_ = this.field.getDeclaringClass();
    }
    public void setField(String name,Class class_)
    {
        try
        {
            this.field = class_.getField(name);
            this.class_ = class_;
        }
        catch (Exception ignored)
        {

        }
    }
    public Class getClass_() {
        return class_;
    }

    public void setClass_(Class class_) {
        this.class_ = class_;
    }
}
