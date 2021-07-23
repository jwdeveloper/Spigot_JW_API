package jw.map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapDrawer
{
    private ItemStack mapItemStack;
    private MapView mapView;
    private PixelRenderer pixelRenderer;

    public MapDrawer(World world)
    {
        mapItemStack = new ItemStack(Material.FILLED_MAP);
        mapView = Bukkit.createMap(world);
        mapView.setScale(MapView.Scale.FARTHEST);
        mapView.setTrackingPosition(false);
        mapView.setUnlimitedTracking(false);
        mapView.setLocked(true);

        MapMeta meta  = (MapMeta)mapItemStack.getItemMeta();
        meta.setMapId(mapView.getId());
        meta.setMapView(mapView);
        mapItemStack.setItemMeta(meta);

        pixelRenderer = new PixelRenderer();
        for(MapRenderer mapRenderer:mapView.getRenderers())
        {
            mapView.removeRenderer(mapRenderer);
        }
        mapView.addRenderer(pixelRenderer);

        MapEventsHandler.Instnace().registerMap(this);
    }

    public int getId()
    {
        return mapView.getId();
    }
    public void setPixel(int x, int y, Color color)
    {
        pixelRenderer.setPixel(x,y,color);
    }
    public void setPixel(int x, int y,int r,int g,int b)
    {
        pixelRenderer.setPixel(x,y,r,b,g);
    }

    public void fillColor(Color color)
    {
        pixelRenderer.fillColor(color);
    }

    public void open(Player player)
    {
        player.getInventory().addItem(mapItemStack);
    }

    public void disable()
    {
        for(MapRenderer mapRenderer:mapView.getRenderers())
        {
            mapView.removeRenderer(mapRenderer);
        }
    }
}
