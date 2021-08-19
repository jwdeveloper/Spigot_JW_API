package jw.map;

import jw.InitializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.HashMap;

public class MapEventsHandler implements Listener
{

    private static MapEventsHandler instnace;
    private static HashMap<Integer,MapDrawer> mapDrawers = new HashMap<>();


    public MapEventsHandler() {
        Bukkit.getPluginManager().registerEvents(this, InitializerAPI.getPlugin());
    }

    public static MapEventsHandler Instnace() {
        if (instnace == null) {
            instnace = new MapEventsHandler();
        }
        return instnace;
    }

    public void registerMap(MapDrawer mapView)
    {
        if(!mapDrawers.containsKey(mapView.getId()))
          mapDrawers.put(mapView.getId(),mapView);
    }
    public void unregisterMap(MapDrawer mapView)
    {
        mapDrawers.remove(mapView.getId());
    }

    public MapDrawer getMapById(int id)
    {
        return mapDrawers.get(id);
    }

    @EventHandler
    public void mapOpenEvent(MapInitializeEvent event)
    {

    }

    public HashMap<Integer, MapDrawer> getMapDrawers()
    {
        return mapDrawers;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == InitializerAPI.getPlugin())
        {
            mapDrawers.forEach( (k,v) ->
            {
                v.disable();
            });

        }
    }



}
