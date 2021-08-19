package jw.events;

import jw.InitializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class EventBase implements Listener
{
    public EventBase()
    {
        Bukkit.getPluginManager().registerEvents(this, InitializerAPI.getPlugin());
    }
}
