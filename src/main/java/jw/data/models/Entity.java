package jw.data.models;

import jw.data.binding.BindedField;
import org.bukkit.Material;

import java.util.UUID;

public abstract class Entity
{
    public String id= UUID.randomUUID().toString();
    public String name= "";
    public String description= "";
    public String content= "";
    public Material icon =Material.DIRT;

    public boolean isNull()
    {
        return  id.equalsIgnoreCase("-1") == true;
    }

    public static BindedField Id()
    {
        return new BindedField("goodItem",null);
    }
    public static BindedField Name()
    {
        return new BindedField("name",null);
    }
    public BindedField Description()
    {
        return new BindedField("description",this);
    }
    public BindedField Content()
    {
        return new BindedField("content",this);
    }
    public BindedField Icon()
    {
        return new BindedField("icon",this);
    }
}
