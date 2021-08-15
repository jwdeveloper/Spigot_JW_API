package jw.utilites.player;


import com.mojang.authlib.GameProfile;
import jw.packets.NMSManager;
import jw.packets.ReflectionUtility;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class NickUtility
{
    public static void changeName(Player player, String name, Collection< ? extends Player> players)
    {
        if(name.length()>16)
            name = name.substring(0,16);

        try
        {
            Object handle = ReflectionUtility.getHandle(player);
            EntityPlayer handleEntityPlayer =(EntityPlayer)handle;

            Field nameField = handleEntityPlayer.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(handleEntityPlayer, name);

            for (Player pl :players)
            {
                if(pl== player)
                    continue;

                PacketPlayOutEntityDestroy destroyEntityPacket = new PacketPlayOutEntityDestroy(player.getEntityId());
                PacketPlayOutPlayerInfo  removePlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.c,handleEntityPlayer);
                PacketPlayOutPlayerInfo  addPlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a,handleEntityPlayer);
                PacketPlayOutNamedEntitySpawn spawnEntity = new PacketPlayOutNamedEntitySpawn(handleEntityPlayer);

                ReflectionUtility.sendPacket(pl,removePlayerPacket);
                ReflectionUtility.sendPacket(pl,destroyEntityPacket);
                ReflectionUtility.sendPacket(pl,addPlayerPacket);
                ReflectionUtility.sendPacket(pl,spawnEntity);
            }

        }
        catch (Exception e)
        {
         Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

}