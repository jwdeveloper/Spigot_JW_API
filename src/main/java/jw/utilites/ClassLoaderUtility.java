package jw.utilites;

import jw.utilites.files.FileHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassLoaderUtility {

    public  <T> Type getTypeFromGenetic()
    {
        try
        {
            var className = ((Class<T>)((ParameterizedType)getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[0])
                    .getTypeName();
            var obj =  Class.forName(className.replace("class ", ""));
            return  obj.getClass();
        }
        catch (Exception e)
        {

        }
        return  null;
    }
    public static  List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = FileHelper.pluginsPath();
        ArrayList<String> files = FileHelper.getFolderFilesName(path, "jar");
        for (String file : files) {
            File jar = new File(path + File.separator + file);
            try {
                JarInputStream is = new JarInputStream(new FileInputStream(jar));
                JarEntry entry;
                while ((entry = is.getNextJarEntry()) != null) {
                    String name = entry.getName();
                    if (name.endsWith(".class")) {
                        String classPath = name.substring(0, entry.getName().length() - 6);
                        classPath = classPath.replaceAll("[\\|/]", ".");
                        if (classPath.contains(packageName)) {
                            classes.add(Class.forName(classPath));
                        }
                    }
                }
            } catch (Exception ex) {
                Bukkit.getConsoleSender().sendMessage(ex.getMessage());
            }
        }
        return classes;
    }
}