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

    private static Class craftplayer_class;
    private static Class packetPlayOutPlayerInfoClass;
    private static Class enumPlayerInfoAction;
    private static Class entityPlayer;
    private static Class entityHuman;
    private static Class packetPlayOutEntityDestroy;
    private static Class packetPlayOutNamedEntitySpawn;
    private static Constructor packetPlayOutNamedEntitySpawnConsturctor;
    private static Constructor packetPlayOutPlayerInfoConsturctor;
    private static Constructor packetPlayOutEntityDestroyConsturctor;

    private static boolean Inicalized = false;

    //1.16.5
    private static void load()
    {
        if(Inicalized)
            return;
        try
        {
            craftplayer_class = NMSManager.getBukkitClass("entity.CraftPlayer");
            packetPlayOutPlayerInfoClass = NMSManager.getNMSProtocol("PacketPlayOutPlayerInfo");
            enumPlayerInfoAction = NMSManager.getNMSProtocol("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
            entityPlayer = NMSManager.getNMSClass("EntityPlayer");
            entityHuman = NMSManager.getNMSClass("EntityHuman");
            packetPlayOutEntityDestroy = NMSManager.getNMSProtocol("PacketPlayOutEntityDestroy");
            packetPlayOutNamedEntitySpawn = NMSManager.getNMSProtocol("PacketPlayOutNamedEntitySpawn");

            packetPlayOutNamedEntitySpawnConsturctor = packetPlayOutNamedEntitySpawn.getConstructor(entityHuman);
            packetPlayOutPlayerInfoConsturctor = packetPlayOutPlayerInfoClass.getConstructor(enumPlayerInfoAction, Array.newInstance(entityPlayer, 1).getClass());
            packetPlayOutEntityDestroyConsturctor = packetPlayOutEntityDestroy.getConstructor(int[].class);
            Inicalized = true;
        }
        catch (NoSuchMethodException e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED+"Error change nick players");
            Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
        }
    }


    public static void changeName(Player player, String name, Collection< ? extends Player> players)
    {
        if(name.length()>16)
            name = name.substring(0,16);

        try
        {
            Object handle = ReflectionUtility.getHandle(player);
            EntityPlayer handleEntityPlayer =(EntityPlayer)handle;

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


    public static void changeNameOld(Player player, String name, Collection< ? extends Player> players)
    {
        load();

        if(name.length()>16)
            name = name.substring(0,16);
        try
        {
            Object craftplayer_player =  craftplayer_class.cast(player);
            Object gameprofile =   NMSManager.getMethod("getProfile",craftplayer_class).invoke(craftplayer_player);

            Field nameField = gameprofile.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(gameprofile, name);


            Object handle = ReflectionUtility.getHandle(player);
            Object handleEntityPlayer =entityPlayer.cast(handle);
            Object entityPlayerArray =   Array.newInstance(entityPlayer, 1);
            Array.set(entityPlayerArray,0,handleEntityPlayer);
            int[] player_id_array = new int[1];
            player_id_array[0] = player.getEntityId();

            Object remove_player_packet = null;
            Object add_player_packet = null;
            Object destory_entity= null;
            Object spawn_entity= null;

            for (Player pl :players)
            {
                if (pl == player) continue;

                remove_player_packet = packetPlayOutPlayerInfoConsturctor.newInstance(Enum.valueOf(enumPlayerInfoAction, "REMOVE_PLAYER"), entityPlayerArray);
                add_player_packet = packetPlayOutPlayerInfoConsturctor.newInstance(Enum.valueOf(enumPlayerInfoAction, "ADD_PLAYER"), entityPlayerArray);
                destory_entity = packetPlayOutEntityDestroyConsturctor.newInstance(player_id_array);
                spawn_entity = packetPlayOutNamedEntitySpawnConsturctor.newInstance(handleEntityPlayer);

                ReflectionUtility.sendPacket(pl,remove_player_packet);
                ReflectionUtility.sendPacket(pl,add_player_packet);
                ReflectionUtility.sendPacket(pl,destory_entity);
                ReflectionUtility.sendPacket(pl,spawn_entity);
            }
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchFieldException  | InstantiationException e)
        {
            Bukkit.getServer().getConsoleSender().sendMessage("Error change nick players");
            Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
        }
    }


    //The less efficient way
/*    public static  void ChangeName(Player player, String name)
    {
        if(name.length()>16)
            name = name.substring(0,16);
        GameProfile gp = ((CraftPlayer)player).getProfile();
        try {
            Field nameField = GameProfile.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(gp, name);
        } catch (IllegalAccessException | NoSuchFieldException ex)
        {
            throw new IllegalStateException(ex);
        }
        for (Player pl : Bukkit.getOnlinePlayers())
        {
            if (pl == player) continue;
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle()));
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
            ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)player).getHandle()));
        }
    }*/



}