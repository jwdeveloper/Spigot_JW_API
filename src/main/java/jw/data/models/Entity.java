package jw.data.models;

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
}
