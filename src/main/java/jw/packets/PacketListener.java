package jw.packets;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class PacketListener
{
    private void injectPlayer(Player player) {
        PacketPlayOutBlockBreakAnimation packetPlayOutBlockBreakAnimation = new PacketPlayOutBlockBreakAnimation(null);

        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET WRITE: " + ChatColor.GREEN + packet.toString());
                super.write(channelHandlerContext, packet, channelPromise);
            }


        };


      //  PlayerBedEnterEvent

      //  this.getWorldServer().getChunkProvider().broadcastIncludingSelf(this, new PacketPlayOutAnimation(this, 2));
     //  ChannelPipeline pipeline = ((CraftPlayer) player)..conn.networkManager.channel.pipeline();
       // pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }


    public static void playSleepAnimation(Player asleep) {

        Location location = asleep.getLocation();

        BlockPosition blockPosition = new BlockPosition(location.getBlockX(),location.getBlockY()-2,location.getBlockZ());
        blockPosition= new BlockPosition(location.getBlock().getX(), location.getBlock().getY(), location.getBlock().getZ());
      //  ((CraftPlayer) asleep).getHandle().sleep(blockPosition,true);

     //   Packet<PacketListenerPlayOut> sleep = new PacketPlayOutPlayerInfo();
     //   ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(sleep);
    }

    public static void setToBed(Player p) {

        /*EntityPlayer entityPlayer = ((CraftPlayer)p).getHandle();
        entityPlayer.setPosition(p.getLocation().getX(), p.getLocation().getY()-0.5, p.getLocation().getZ());
        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(entityPlayer);

        entityPlayer.b.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityPlayer));
        entityPlayer.b.sendPacket(packetPlayOutNamedEntitySpawn);

        Vec3D pos = new Vec3D(p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
        entityPlayer.e(pos);

        entityPlayer.b.sendPacket(new PacketPlayOutEntityMetadata(entityPlayer.getId(), entityPlayer.getDataWatcher(), false));
        DataWatcher watcher = entityPlayer.getDataWatcher();

        entityPlayer.b.sendPacket(new PacketPlayOutEntityMetadata(entityPlayer.getId(), watcher, false));*/
    }

}
