package jw;
import jw.gui.core.InventoryGUIEventsHander;
import jw.map.MapEventsHandler;
import org.bukkit.plugin.Plugin;

public final class InicializerAPI
{
    private  static  Plugin plugin;

    public static Plugin GetPlugin()
    {
        return  plugin;
    }

    public static void AttachePlugin(Plugin plugin)
    {
        InicializerAPI.plugin = plugin;
        InventoryGUIEventsHander.Instnace();
        MapEventsHandler.Instnace();
    }

}
