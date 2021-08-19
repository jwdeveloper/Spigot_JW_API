package jw;
import jw.data.repositories.DataManager;
import jw.dependency_injection.ServiceManager;
import jw.gui.core.InventoryGUIEventsHandler;
import jw.logs.LoggerManager;
import jw.map.MapEventsHandler;
import jw.utilites.ConsoleUtility;
import jw.utilites.Emoticons;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class InitializerAPI
{
    private static Plugin plugin;
    private static LoggerManager logger;
    private static DataManager dataManager;
    public static Plugin getPlugin()
    {
        return  plugin;
    }
    public static Logger logger()
    {
         return logger.getLogger();
    }
    public static void errorLog(String message)
    {
        log(ConsoleUtility.RED,message);
    }
    public static void infoLog(String message)
    {
        log(ConsoleUtility.YELLOW,message);
    }
    public static void successLog(String message)
    {
        log(ConsoleUtility.GREEN,message);
    }

    public static void attachePlugin(Plugin plugin)
    {
        InitializerAPI.plugin = plugin;
        InitializerAPI.logger = new LoggerManager("JW API -"+plugin.getName());
        dataManager = new DataManager();
        ServiceManager.Instance();
        InventoryGUIEventsHandler.Instance();
        MapEventsHandler.Instnace();
        ServiceManager.Instance();
    }

    private static void log(String color,String message)
    {
        logger().log(Level.INFO, color+Emoticons.arrowright+" "+message+ConsoleUtility.RESET);
    }

    public static void useDependencyInjection()
    {
         if(isPluginAttached())
         {
             ServiceManager.registerAllFromPackage(plugin.getClass().getPackage());
             dataManager.attacheServices();
         }
    }

    public static DataManager getDataManager()
    {
        if(isPluginAttached())
        {
            return dataManager;
        }
        return null;
    }

    private static boolean isPluginAttached()
    {
        if(plugin == null)
        {
            errorLog("Plugin is not attached! Use InitializerAPI.attachePlugin()");
            return false;
        }
        return true;
    }



}
