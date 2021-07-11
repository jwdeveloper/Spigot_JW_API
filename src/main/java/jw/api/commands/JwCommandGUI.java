package jw.api.commands;

import jw.api.gui.core.InventoryGUI;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class JwCommandGUI extends JwCommand {
    private HashMap<UUID, InventoryGUI> playersGui = new HashMap<UUID, InventoryGUI>();

    public JwCommandGUI(String name) {
        super(name);
    }

    public abstract InventoryGUI SetInventoryGUI();

    public  void Invoke(Player playerSender, String[] args)
    {
        this.GetGUI(playerSender).Open(playerSender);
    }

    public void  Invoke(ConsoleCommandSender serverSender, String[] args)
    {
    }

    public abstract void OnInitialize();

    public void CloseGUI() {
        playersGui.forEach((a, b) ->
        {
            playersGui.get(a).Close();
        });
    }

    public InventoryGUI GetGUI(Player player) {
        if (playersGui.containsKey(player.getUniqueId()))
            return playersGui.get(player.getUniqueId());

        InventoryGUI gui = SetInventoryGUI();
        gui.SetPlayer(player);
        playersGui.put(player.getUniqueId(), gui);
        return gui;
    }


}
