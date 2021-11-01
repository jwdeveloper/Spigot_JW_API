package jw.minigame;

import jw.data.repositories.Saveable;
import org.bukkit.Location;
import org.bukkit.World;

public class MinigameConfig implements Saveable
{
    public World world;
    public String name;
    public String permission;
    public int maxPlayers =0;
    public boolean allowLogOut = false;
    public Location lobbySpawnPoint;
    public Location gameSpawnPoint;

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public boolean save() {
        return false;
    }


}
