package jw.utilites.binding;

import java.util.function.Consumer;

public interface Bindable<T>
{
     void set(T value);

     T get();
}
