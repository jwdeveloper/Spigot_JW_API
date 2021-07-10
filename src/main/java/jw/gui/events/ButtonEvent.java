package jw.gui.events;

import jw.gui.button.Button;
import org.bukkit.entity.Player;

public interface ButtonEvent
{
    void Execute(Player player, Button button);
}
