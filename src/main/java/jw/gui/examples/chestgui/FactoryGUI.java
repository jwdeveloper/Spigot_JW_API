package jw.gui.examples.chestgui;

import jw.gui.examples.ChestGUI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class FactoryGUI
{
    private static FactoryGUI instance;
    private HashMap<UUID, ChestGUI> selectiveLists = new HashMap<>();

    private static FactoryGUI getInstance()
    {
        if(instance == null)
            instance = new FactoryGUI();
        return instance;
    }

    private ChestGUI getSelectiveList(Player player)
    {
        if(!selectiveLists.containsKey(player))
        {
            selectiveLists.put(player.getUniqueId(),new ListGUI(null,"A",6));
        }
        return selectiveLists.get(player);
    }

    public static ChestGUI selectiveList(Player player)
    {
        return getInstance().getSelectiveList(player);
    }

}
