package jw.data.binding;

import java.lang.reflect.Field;

public class BindedField
{
    private Field field;
    private Object object;
    public BindedField(String fieldname,Object object)
    {
        try
        {
            this.object =object;
            field = object.getClass().getField(fieldname);
        }
        catch (Exception exception)
        {

        }
    }

    public void SetObject(Object object)
    {
        if(object == null)
            return;

        this.object = object;
    }

    public Object getValue()
    {
        if(field ==null)
            return null;
        try
        {
            return field.get(object);
        }
        catch (Exception exception)
        {

        }
       return null;
    }
    public void setValue(Object ... args)
    {
        if(field ==null)
            return;
        try
        {
           field.set(object,args);
        }
        catch (Exception exception)
        {

        }
    }
}
