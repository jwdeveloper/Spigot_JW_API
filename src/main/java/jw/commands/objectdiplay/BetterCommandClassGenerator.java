package jw.commands.objectdiplay;

import jw.commands.BetterCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

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
        for(Method method:_class.getMethods())
        {
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
