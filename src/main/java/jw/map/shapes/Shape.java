package jw.map.shapes;

public abstract class Shape
{
   private int x;
   private int y;

   public Shape(int x,int y)
   {
       this.x = x;
       this.y= y;
   }

    public abstract void draw(byte[][] pixels);
}
