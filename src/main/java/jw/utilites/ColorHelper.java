package jw.utilites;



import net.md_5.bungee.api.ChatColor;


import org.bukkit.DyeColor;


import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorHelper
{
    private static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static ChatColor getColor(int r, int g, int b)
    {
        return  ChatColor.of(new Color(r,g,b));
    }

    private char convertHexColorToNearestMinecraftColor(String hexColor) {
        java.awt.Color jColor = java.awt.Color.decode(hexColor);
      //   DyeColor dColor = DyeColor.getByColor(Color.fromRGB(jColor.getRed(), jColor.getGreen(), jColor.getBlue())); // null
      //  new ColorConverter();
      //  ChatColor cColor = ColorConverter.dyeToChat(dColor);
      //  return cColor.getChar();
        return '*';
    }

    public static String format(String msg)
    {

        Matcher matcher  = pattern.matcher(msg);
        while (matcher.find())
        {
            String color =msg.substring(matcher.start(),matcher.end());
            msg = msg.replace(color,ChatColor.of(color)+"");
            matcher =pattern.matcher(msg);

        }
        return ChatColor.translateAlternateColorCodes('&',msg);
    }
}
