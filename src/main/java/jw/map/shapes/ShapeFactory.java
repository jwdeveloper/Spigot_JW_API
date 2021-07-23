package jw.map.shapes;

public class ShapeFactory
{

    public static Circle circle(int x, int y,int radious)
    {
        return new Circle(x,y,radious);
    }

    public static ReactAngle reactAngle(int x, int y,int width,int height)
    {
        return new ReactAngle(x,y,width,height);
    }

}
