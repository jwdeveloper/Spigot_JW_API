package jw.events;

import jw.InitializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import java.util.function.Consumer;

public class FluentEvents extends EventBase
{
    private static FluentEvents instnace;
    private static FluentEvents getInstnace()
    {
        if(instnace == null)
            instnace = new FluentEvents();

        return  instnace;
    }

    public static <T extends Event> FluentEvent<T> onEvent(Class<T> eventType,Consumer<T> action)
    {
        var fluentEvent = new FluentEvent<T>(action);
        Bukkit.getPluginManager().registerEvent(eventType, getInstnace(), EventPriority.NORMAL,
                (listener, event) ->
                {
                    fluentEvent.invoke((T)event);
                }, InitializerAPI.getPlugin());
        return fluentEvent;
    }

    public static <T extends Event> FluentEvent<T> onEventAsync(Class<T> tClass,Consumer<T> action)
    {

        var fluentEvent = new FluentEvent<T>(action);
        Bukkit.getPluginManager().registerEvent(tClass, getInstnace(), EventPriority.NORMAL,
                (listener, event) ->
                {
                    Bukkit.getScheduler().runTask(InitializerAPI.getPlugin(),()->
                    {
                        fluentEvent.invoke((T)event);
                    });
                }, InitializerAPI.getPlugin());
        return fluentEvent;
    }
}
