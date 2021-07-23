package jw.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ScreenRenderer extends MapRenderer {

    private byte[][] screen;
    private int width;
    private int height;

    public void setScreenArray(byte[][] array, int width, int height) {
        this.screen = array;
        this.width = width * ScreenUtility.MAX_MAP_DIMENTIONS;
        this.height = height * ScreenUtility.MAX_MAP_DIMENTIONS;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {

        for (int y = height; y < height + ScreenUtility.MAX_MAP_DIMENTIONS; y++) {
            for (int x = width; x < width + ScreenUtility.MAX_MAP_DIMENTIONS; x++) {
                 mapCanvas.setPixel(y,x,screen[y][x]);
            }
        }
    }
}
