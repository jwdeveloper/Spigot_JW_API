package jw.utilites;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class ObjectHelper {
    public static <T> List<T> GetObjectList(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void CopyToObject(Object obj, Object obj2, Class type) {
        Field[] files = type.getFields();
        for (Field file : files) {
            try {
                file.setAccessible(true);
                file.set(obj2, file.get(obj));
            } catch (Exception ignored) {

            }
        }
    }
    @SuppressWarnings("unchecked")
    public <T> Class<T> GetGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }
    public static Field FindProperty(String property_name, Class property_type) {
        for (Field f : property_type.getFields()) {
            if (f.getName().contains(property_name)) {
                return f;
            }
        }
        return null;
    }
    public static <T extends Enum<T>> T EnumToString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }


    public static void SetValue(Object obj,String name,Object value){
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        }catch(Exception e){}
    }

    public static Object GetValue(Object obj,String name){
        Object result=null;
        try{
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(obj);
            field.setAccessible(false);

        }catch(Exception e){}

        return result;
    }
}
