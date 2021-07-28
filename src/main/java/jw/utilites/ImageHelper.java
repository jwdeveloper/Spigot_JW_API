package jw.utilites;

import org.bukkit.ChatColor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper
{
    public static BufferedImage getImage(String filename) {
        try {
            return  ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(ChatColor.RED+"Image load error "+e.getMessage()+" "+filename);
        }

        return null;
    }
}
