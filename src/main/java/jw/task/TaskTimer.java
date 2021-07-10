package jw.task;


import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class TaskTimer
{
    public interface  I_Task
    {
        public void execute(int time, TaskTimer taskTimer);
    }

    I_Task task;

    Consumer onstop;
    int speed =20;
    int time = 0;
    int stopAfter = 99999999;
    int runafter = 0;
    boolean iscancel = false;
    BukkitTask taskId ;
    public TaskTimer(int tick,I_Task task)
    {
        this.speed = tick;
        this.task = task;
    }

    public void Stop()
    {
        if(taskId!=null)
            taskId.cancel();
    }


    public void Cancel()
    {
        iscancel =true;
    }
    public TaskTimer StartAfter(int i)
    {
        this.runafter = i;
        return this;
    }

    public TaskTimer StopAfter(int i)
    {
        this.stopAfter = i;
        return this;
    }
    public TaskTimer OnStop(Consumer task)
    {
        this.onstop = task;
        return this;
    }


    public void RunAgain()
    {
        this.time = 0;
        this.iscancel = false;
    }


    public void RunAsync()
    {
        taskId= Bukkit.getScheduler().runTaskTimerAsynchronously(InicializerAPI.GetPlugin(),()->
        {
            if(time>=stopAfter || iscancel)
            {
                if(this.onstop!=null)
                    this.onstop.accept(null);

                Stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runafter,speed);
    }
    public void Run()
    {
        taskId= Bukkit.getScheduler().runTaskTimer(InicializerAPI.GetPlugin(),()->
        {
            if(time>=stopAfter || iscancel)
            {
                if(this.onstop!=null)
                    this.onstop.accept(null);

                Stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runafter,speed);
    }

}