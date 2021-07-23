package jw.map.shapes;

public class ReactAngle extends Shape
{
   private int width;
   private int height;


    public ReactAngle(int x, int y,int width,int height)
    {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(byte[][] pixels)
    {

    }
}
