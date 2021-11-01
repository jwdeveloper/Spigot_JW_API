package jw.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class GenericToType<T extends Event>
{
    public   Class<T> convert()
    {
       return (Class<T>)((ParameterizedType)
                getClass()
               .getGenericSuperclass())
               .getActualTypeArguments()[0];
    }


    public <X> void test(X dupa)
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+ "DUPPPPPPPPPPPPPPPPPPPPA");
        try
        {
            Method m = getClass().getMethods()[0];
            Type[] genericParameterTypes = m.getGenericParameterTypes();
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+""+ genericParameterTypes.length);
            for (int i = 0; i < genericParameterTypes.length; i++)
            {
                Bukkit.getConsoleSender().sendMessage(i+" P "+this.getClass().getGenericSuperclass().getTypeName());

                if( genericParameterTypes[i] instanceof ParameterizedType )
                {
                    Bukkit.getConsoleSender().sendMessage(i+" P is PT");
                    Type[] parameters = ((ParameterizedType)genericParameterTypes[i]).getActualTypeArguments();
                    Bukkit.getConsoleSender().sendMessage(i+" WORK" + parameters[0].getTypeName());
                }

            }
        }
        catch (Exception e)
        {
           Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"ERROR"+ e.getMessage());
        }

    }
}
