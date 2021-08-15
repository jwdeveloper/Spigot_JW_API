package jw.utilites;

import jw.task.TaskTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;

public class VectorsHelper
{








    public static void DisplayDirection(Location location,int distance)
    {
        new TaskTimer(20, (time, task) ->
        {
            for(int i=0;i<distance;i++)
            {
                Location particleLoc =  location.clone().add( location.getDirection().multiply(i));
                location.getWorld().spawnParticle(Particle.HEART,particleLoc,1);
            }

        }).stopAfter(20).run();
    }

}
