package jw.data.repositories;

import jw.utilites.files.FileHelper;
import jw.utilites.ObjectHelper;
import jw.utilites.files.JsonFileHelper;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryManager
{
    protected String path;
    protected List<Repository> repositories = new ArrayList<>();
    protected List<Object> objects = new ArrayList<>();

    public RepositoryManager()
    {
        path =  FileHelper.pluginPath();
    }
    public RepositoryManager(String path)
    {
        this.path = path;
    }

    public void AddRepository(Repository repository)
    {
        repositories.add(repository);
    }
    public void AddObject(Object obj)
    {
        objects.add(obj);
    }
    public void Load()
    {
        for (Repository repository : repositories) {
            repository.loadData();
        }
        for (int i=0;i<this.objects.size();i++)
        {
            try
            {
                Object  loaded_object= JsonFileHelper.load(path,this.objects.get(i).getClass().getSimpleName(),this.objects.get(i).getClass());

                if(loaded_object != null)
                {
                    ObjectHelper.CopyToObject(loaded_object,objects.get(i),loaded_object.getClass());
                }

            }
            catch (Exception e)
            {
                Bukkit.getServer().getConsoleSender().sendMessage("Loading obj from file " +e.getMessage().toString());
            }
        }
    }
    public void Save()
    {
        for (Repository repository : repositories)
        {
            repository.saveData();
        }
        for (Object object : objects)
        {
            JsonFileHelper.save(object,path,object.getClass().getSimpleName());
        }
    }
}