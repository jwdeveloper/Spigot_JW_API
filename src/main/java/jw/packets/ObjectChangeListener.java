package jw.packets;

public interface ObjectChangeListener<T>
{

    public void OnChange(Object object, T newValue);

}
