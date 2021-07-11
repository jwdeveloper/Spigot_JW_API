package jw.api.utilites.player;

import jw.api.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;

public class PermissionUtility
{
    public static void GivePermission(Player player, String permission)
    {
        PermissionAttachment attachment = player.addAttachment(InicializerAPI.GetPlugin());
        attachment.setPermission(permission,true);
    }

    public static ArrayList<String> GetPermissions(Player player)
    {
        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
        {
            Bukkit.getConsoleSender().sendMessage(permissionAttachmentInfo.getPermission());
        });


        return  new ArrayList<>();
    }

}
