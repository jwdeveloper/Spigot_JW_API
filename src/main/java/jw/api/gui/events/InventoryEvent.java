package jw.api.gui.events;
import jw.api.gui.button.Button;
import org.bukkit.entity.Player;

public interface InventoryEvent
{
    public void Execute(Player player, Button button);
}
