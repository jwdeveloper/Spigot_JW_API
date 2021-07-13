package jw.utilites.player;

import org.bukkit.entity.Player;

public class SoundUtility
{
    public static void playerSound(Player player, String soundName)
    {
        try {
            player.playSound(player.getLocation(), org.bukkit.Sound.valueOf(soundName), 1.0F, 1.0F);
        } catch (IllegalArgumentException var3) {
        }
    }
}
