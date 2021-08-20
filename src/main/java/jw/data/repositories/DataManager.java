package jw.data.repositories;

import jw.dependency_injection.InjectionManager;
import jw.utilites.files.FileHelper;
import jw.utilites.ObjectHelper;
import jw.utilites.files.JsonFileHelper;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    protected String path;
    protected List<Saveable> objects = new ArrayList<>();

    public DataManager() {
        this(FileHelper.pluginPath());
    }

    public DataManager(String path)
    {
        this.path = path;

    }

    public void attacheServices()
    {
        objects.addAll(InjectionManager.getObjectByType(Saveable.class));
    }

    public List<Saveable> getObjects()
    {
        return objects;
    }

    public void addObject(Saveable object) {
        objects.add(object);
    }

    public void load() {
        for (Saveable object : objects) {
            if (object instanceof Repository)
                object.load();
            else {
                try {
                    Object loadedObject = JsonFileHelper.load(
                            path,
                            object.getClass().getSimpleName(),
                            object.getClass());

                    if (loadedObject != null) {
                        ObjectHelper.copyToObject(loadedObject, object, loadedObject.getClass());
                    }

                } catch (Exception e) {
                    Bukkit.getServer().getConsoleSender().sendMessage("Loading obj from file " + e.getMessage().toString());
                }
            }

        }
    }

    public void save() {
        for (Saveable object : objects) {
            if (object instanceof Repository)
                object.load();
            else
                JsonFileHelper.save(object, path, object.getClass().getSimpleName());
        }
    }
}