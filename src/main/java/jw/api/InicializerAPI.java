package jw.api;
import jw.api.gui.core.InventoryGUIEventsHander;
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
        OnPluginAttached(plugin);
    }

    private static void OnPluginAttached(Plugin plugin)
    {
        InventoryGUIEventsHander.Instnace();
    }
}
