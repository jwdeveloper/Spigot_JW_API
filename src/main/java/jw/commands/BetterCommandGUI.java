package jw.commands;

import jw.InitializerAPI;
import jw.dependency_injection.InjectionManager;
import jw.gui.core.InventoryGUI;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class BetterCommandGUI extends BetterCommand
{

    private final Class<? extends InventoryGUI> guiType;
    public BetterCommandGUI(String name,Class<? extends InventoryGUI> guiType)
    {
        super(name);
        this.guiType = guiType;
    }
    public void onInvoke(Player playerSender, String[] args)
    {
        this.getGUI(playerSender).open(playerSender);
    }

    public void onInvoke(ConsoleCommandSender serverSender, String[] args)
    {
        InitializerAPI.errorLog("GUI "+guiType.getSimpleName()+" can't be open by console!");
    }
    public abstract void onInitialize();

    protected InventoryGUI getGUI(Player player)
    {
        return InjectionManager.getObjectPlayer(guiType,player.getUniqueId());
    }
}
