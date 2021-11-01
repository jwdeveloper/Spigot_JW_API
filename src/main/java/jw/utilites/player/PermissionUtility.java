package jw.utilites.player;

import jw.InitializerAPI;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class PermissionUtility
{
    public static void givePermission(Player player, String permission)
    {
        var attachment = player.addAttachment(InitializerAPI.getPlugin());
        attachment.setPermission(permission,true);
    }

    public static void removePermission(Player player, String permission)
    {
        player.getEffectivePermissions().forEach(permissionAttachmentInfo ->
        {
            permissionAttachmentInfo.getAttachment().getPermissions();
        });
    }

    public static Object[] getPermissions(Player player)
    {
        return  player.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).toArray();
    }

}
