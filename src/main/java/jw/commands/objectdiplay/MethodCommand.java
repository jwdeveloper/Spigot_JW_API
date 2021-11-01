package jw.commands.objectdiplay;

import jw.commands.FluentCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public class MethodCommand extends FluentCommand
{
    private Method method;
    private Object object;
    private final HashMap<Integer,Class> parametets = new HashMap<>();
    public MethodCommand(Object object,Method method)
    {
        super(method.getName(),false);
        this.method=method;
        this.object =object;
        createCommand();
        displaySubCommandsNames();
    }
    private void createCommand()
    {
        Parameter parameter = null;
        for(int i=0;i<method.getParameterCount();i++)
        {
            parameter = method.getParameters()[i];
            parametets.put(i,parameter.getType());
            this.setTabCompleter(i+1,ChatColor.YELLOW+parameter.getName()+" ("+parameter.getType().getSimpleName()+")");
        }
    }
    public void invokeMethod(String[] args)
    {
        if(args.length != method.getParameterCount())
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Not enought parameters, expeted: "+method.getParameterCount());
            return;
        }
        try
        {
            Object[] objects = new Object[method.getParameterCount()];
            for(int i=0;i<method.getParameterCount();i++)
            {
                String obj = args[i];
                switch(parametets.get(i).getName())
                {
                    case "boolean":
                        obj = obj.toLowerCase();
                        if(obj.equals("true"))
                            objects[i] = true;
                        else
                            objects[i] =false;
                        break;
                    case "int":
                        objects[i] = Integer.parseInt(obj);
                        break;
                    default:
                        objects[i] = obj;
                }
            }
            method.invoke(object,objects);
        }
        catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Error: "+e.getMessage());
        }
    }

    @Override
    public void onInvoke(Player playerSender, String[] args)
    {
        invokeMethod(args);
    }

    @Override
    public void onInvoke(ConsoleCommandSender serverSender, String[] args)
    {
        invokeMethod(args);
    }

    @Override
    public void onInitialize()
    {

    }
}
