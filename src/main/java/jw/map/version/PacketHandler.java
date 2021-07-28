package jw.map.version;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface PacketHandler {

    void display(UUID[] viewers, int map, int mapWidth, int mapHeight, byte[] rgb, int videoWidth, int xOffset, int yOffset );

    void display( UUID[] viewers, int map, int mapWidth, int mapHeight, byte[] rgb, int videoWidth );
    Object onPacketInterceptOut(Player viewer, Object packet );
    Object onPacketInterceptIn( Player viewer, Object packet );
    void registerMap( int id );
    boolean isMapRegistered( int id );
    void unregisterMap( int id );
    void registerPlayer( Player player );
    void unregisterPlayer( UUID uuid );
    ItemStack getMapItem(int id );
}