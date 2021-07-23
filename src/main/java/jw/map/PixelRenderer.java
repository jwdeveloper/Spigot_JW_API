package jw.map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class PixelRenderer extends MapRenderer
{
    private byte[][] matrix = new byte[128][128];

    public PixelRenderer()
    {
        for(int y=0;y<128;y++)
        {
            for(int x=0;x<128;x++)
            {
                matrix[y][x] =11;
            }
        }
    }
    public void fillColor(Color color)
    {
        for(int y=0;y<128;y++)
        {
            for(int x=0;x<128;x++)
            {
                setPixel(x,y,color);
            }
        }
    }

    public void setPixel(int x, int y, Color color)
    {
        setPixel(x,y,color.getRed(),color.getGreen(),color.getBlue());
    }
    public void setPixel(int x, int y, int r,int g,int b)
    {
        if(x >=128 || y >=128)
            return;
        Bukkit.getConsoleSender().sendMessage(r+""+g+""+b);
        matrix[y][x] = MapPalette.matchColor(r,g,b);
    }
    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player)
    {
        for(int y=0;y<128;y++)
        {
            for(int x=0;x<128;x++)
            {
                mapCanvas.setPixel(y,x,matrix[y][x]);
            }
        }
    }
}
