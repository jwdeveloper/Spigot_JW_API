package jw.utilites;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jw.InitializerAPI;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectHelper {
    public static boolean classContainsType(Class<?> type, Class<?> searchType) {
       // InitializerAPI.infoLog(type.getName() + " ");
        while (true) {
            if (type.isAssignableFrom(searchType))
            {
                return true;
            }
            for (var interfaceType : type.getInterfaces()) {
                if (interfaceType.equals(searchType)) {
                    return true;
                }
            }
            type = type.getSuperclass();

            if (type.equals(Object.class)) {
                break;
            }
        }
        return false;
    }


    public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
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

    public static void copyToObject(Object obj, Object obj2, Class type) {
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
    public <T> Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    public static Field findProperty(String property_name, Class property_type) {
        for (Field f : property_type.getFields()) {
            if (f.getName().contains(property_name)) {
                return f;
            }
        }
        return null;
    }

    public static <T extends Enum<T>> T enumToString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }


    public static void setValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
        }
    }

    public static Object getValue(Object obj, String name) {
        Object result = null;
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(obj);
            field.setAccessible(false);

        } catch (Exception e) {
        }

        return result;
    }
}
