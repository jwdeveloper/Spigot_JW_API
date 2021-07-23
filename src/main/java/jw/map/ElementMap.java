package jw.map;

import jw.map.shapes.Shape;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ElementMap extends MapRenderer
{

    private byte[][] pixels = new byte[128][128];

    public void drawShape(Shape shape)
    {
        shape.draw(pixels);
    }

    public void fillColor(Color color)
    {
        for(int y=0;y<128;y++)
        {
            for(int x=0;x<128;x++)
            {
                pixels[y][x] = MapPalette.matchColor(color.getRed(),color.getGreen(),color.getBlue());
            }
        }
    }

    public void setPixel(int x, int y, Color color)
    {
        if(x >=128 || y >=128)
            return;
        pixels[y][x] = MapPalette.matchColor(color.getRed(),color.getGreen(),color.getBlue());
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player)
    {
        for(int y=0;y<128;y++)
        {
            for(int x=0;x<128;x++)
            {
                mapCanvas.setPixel(y,x,pixels[y][x]);
            }
        }
    }
}
