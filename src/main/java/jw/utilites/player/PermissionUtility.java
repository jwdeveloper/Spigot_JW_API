package jw.utilites.player;

import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;

public class PermissionUtility
{
    public static void givePermission(Player player, String permission)
    {
        PermissionAttachment attachment = player.addAttachment(InicializerAPI.getPlugin());
        attachment.setPermission(permission,true);
    }

    public static ArrayList<String> getPermissions(Player player)
    {
        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
        {
            Bukkit.getConsoleSender().sendMessage(permissionAttachmentInfo.getPermission());
        });


        return  new ArrayList<>();
    }

}
