package jw.commands.objectdiplay;

import jw.commands.BetterCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BetterCommandClassGenerator extends BetterCommand
{
    private Object object;
    private Class _class;

    public BetterCommandClassGenerator(Object object)
    {
        this(object.getClass().getSimpleName(),object);
    }
    public BetterCommandClassGenerator(String name,Object object)
    {
        super(name);
        this.object = object;
        this._class = object.getClass();
        createChildren();
        displaySubCommandsNames();
    }

    private void createChildren()
    {
        List<String> ignoredMethods = new ArrayList<>();
        ignoredMethods.add("equals");
        ignoredMethods.add("getClass");
        ignoredMethods.add("hashCode");
        ignoredMethods.add("notify");
        ignoredMethods.add("notifyAll");
        ignoredMethods.add("toString");
        ignoredMethods.add("wait");

        for(Method method:_class.getMethods())
        {
            boolean isIgnored = ignoredMethods.stream()
                                              .anyMatch(ig -> ig.equalsIgnoreCase(method.getName()));
            if(isIgnored)
            {
                continue;
            }

            this.addChild(new MethodCommand(object,method));
        }
    }
    @Override
    public void onInvoke(Player playerSender, String[] args) {

    }

    @Override
    public void onInvoke(ConsoleCommandSender serverSender, String[] args) {

    }

    @Override
    public void onInitialize()
    {

    }
}
