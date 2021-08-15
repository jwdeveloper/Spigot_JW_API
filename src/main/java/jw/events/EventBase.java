package jw.events;

import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class EventBase implements Listener
{
    public EventBase()
    {
        Bukkit.getPluginManager().registerEvents(this, InicializerAPI.getPlugin());
    }
}
