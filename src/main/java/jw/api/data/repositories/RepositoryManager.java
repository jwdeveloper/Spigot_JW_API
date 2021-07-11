package jw.api.data.repositories;

import jw.api.utilites.FileHelper;
import jw.api.utilites.ObjectHelper;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryManager<T extends RepositoryManager<T>>
{

    protected String path;
    protected List<Repository> repositories = new ArrayList<>();
    protected List<Object> objects = new ArrayList<>();

    public RepositoryManager()
    {
        path =  FileHelper.PluginPath();
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
                Object  loaded_object= FileHelper.Load(path,this.objects.get(i).getClass().getSimpleName()+".json",this.objects.get(i).getClass());

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
            try
            {
                object.toString();
                FileHelper.Save(object,path,object.getClass().getSimpleName()+".json");
            }
            catch (Exception e)
            {
                Bukkit.getServer().getConsoleSender().sendMessage("Save obj to file "+e.getMessage().toString());
            }
        }
    }
}