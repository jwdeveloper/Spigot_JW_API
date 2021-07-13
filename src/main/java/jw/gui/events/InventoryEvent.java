package jw.gui.events;
import jw.gui.button.Button;
import org.bukkit.entity.Player;

public interface InventoryEvent
{
    public void Execute(Player player, Button button);
}
