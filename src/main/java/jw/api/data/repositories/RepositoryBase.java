package jw.api.data.repositories;

import jw.api.data.models.Entity;
import jw.api.utilites.FileHelper;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class RepositoryBase<T extends Entity> implements Repository<T> {

    public ArrayList<T> content = new ArrayList<>();
    private String path;
    private String fileName;
    private Class<T> entityClass;

    public Consumer<String> onError = (s) -> {
    };

    public RepositoryBase(String path, Class<T> entityClass) {
        this.path = path;
        this.fileName = entityClass.getSimpleName();
        this.entityClass = entityClass;
    }

    public RepositoryBase(String path, Class<T> entityClass, String filename) {
        this(path, entityClass);
        this.fileName = filename;
    }


    @Override
    public Class<T> getEntityClass() {
        return  entityClass;
    }

    @Override
    public T getOne(String id) {
        Optional<T> data = content.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst();
        return data.orElseGet(this::CreateEmpty);
    }

    public T getOneByName(String name) {
        Optional<T> data = content.stream().filter(p -> ChatColor.stripColor(p.name).equalsIgnoreCase(name)).findFirst();
        return data.orElseGet(this::CreateEmpty);
    }

    @Override
    public ArrayList<T> getMany(HashMap<String, String> args) {
        return content;
    }

    public ArrayList<T> getMany()
    {
        return content;
    }

    @Override
    public boolean insertOne(T data) {
        if(data!=null)
        {
            data.id = UUID.randomUUID().toString();
            content.add(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean insertMany(ArrayList<T> data) {
        data.stream().forEach(a -> this.insertOne(a));
        return true;
    }

    @Override
    public boolean updateOne(String id, T data) {
        return false;
    }

    @Override
    public boolean updateMany(HashMap<String, T> data) {
        return false;
    }

    @Override
    public boolean deleteOne(String id, T data) {
        Optional<T> exist = content.stream().filter(p -> p.id.equalsIgnoreCase(id)).findFirst();
        if(exist.isPresent())
        {
            content.remove(exist.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMany(ArrayList<T> data) {
        data.stream().forEach(a -> this.deleteOne(a.id,a));
        return true;
    }

    public void deleteAll()
    {
        content.clear();
    }

    @Override
    public boolean loadData() {
        try
        {
            content= FileHelper.Load_List(path,fileName+".json",entityClass);
            return true;
        }
        catch (Exception e)
        {
            onError.accept(fileName+".json"+" " +entityClass.getName()+" "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean saveData() {
        try
        {
            FileHelper.Save(content,path,fileName+".json");
            return true;
        }
        catch (Exception e)
        {
            onError.accept(fileName+".json"+" " +entityClass.getName()+" "+e.getMessage());
            return false;
        }
    }

    public T CreateEmpty() {
        try {
            T empty = entityClass.newInstance();
            empty.id  = "-1";
            empty.name = "-1";
            return empty;
        } catch (IllegalAccessException | InstantiationException igonre) {
            onError.accept(igonre.getMessage());
        }
        return null;
    }
}
