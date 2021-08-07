package jw;
import jw.gui.core.InventoryGUIEventsHandler;
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
        InventoryGUIEventsHandler.Instance();
        MapEventsHandler.Instnace();
    }

}
