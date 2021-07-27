package jw;
import jw.gui.core.InventoryGUIEventsHander;
import jw.map.MapEventsHandler;
import org.bukkit.plugin.Plugin;

public final class InicializerAPI
{
    private  static  Plugin plugin;

    public static Plugin getPlugin()
    {
        return  plugin;
    }

    public static void attachePlugin(Plugin plugin)
    {
        InicializerAPI.plugin = plugin;
        InventoryGUIEventsHander.Instnace();
        MapEventsHandler.Instnace();
    }

}
