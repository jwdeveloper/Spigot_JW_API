package jw.events;

import jw.InitializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public abstract class EventBase implements Listener
{
    public EventBase()
    {
        Bukkit.getPluginManager().registerEvents(this, InitializerAPI.getPlugin());
    }

    public void onPluginStart(PluginEnableEvent event)
    {

    }
    public void onPluginStop(PluginDisableEvent event)
    {

    }
    @EventHandler
    public final void onPluginStartEvent(PluginEnableEvent pluginEnableEvent)
    {
          if(pluginEnableEvent.getPlugin() == InitializerAPI.getPlugin())
          {
              onPluginStart(pluginEnableEvent);
          }
    }
    @EventHandler
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == InitializerAPI.getPlugin())
        {
            onPluginStop(pluginDisableEvent);
        }
    }

}
