package jw.utilites;

public class MathHelper
{
    public static float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }
    public static double getPersent(double max, double current)
    {
        if(current > max)
            current = max;
        if(current<=0)
            current =1 ;
        return current/max;
    }

    public static double yawToRotation(float yaw)
    {
        double rotation = (yaw - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         return rotation;
    }
}
