package jw.map.shapes;

public class Circle extends Shape
{

    private int radious;

    public Circle(int x, int y,int radious) {
        super(x, y);
        this.radious = radious;
    }

    @Override
    public void draw(byte[][] pixels) {

    }
}
