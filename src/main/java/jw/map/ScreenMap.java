package jw.map;
import jw.map.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

public class ScreenMap
{
    private int width =1;
    private int height =1;
    private String name;
    private List<ElementMap> maps = new ArrayList<>();
    private byte[][] screen = new byte[ScreenUtility.MAX_MAP_DIMENTIONS][ScreenUtility.MAX_MAP_DIMENTIONS];
    private void setSize(int width,int height)
    {
      this.width = width;
      this.height = height;
      this.screen = new byte[ScreenUtility.MAX_MAP_DIMENTIONS*this.height][ScreenUtility.MAX_MAP_DIMENTIONS*this.width];
    }
    private void drawShape(Shape shape)
    {
          shape.draw(this.screen);
    }

}
