package jw.task;


import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class TaskTimer
{
    public interface TaskAction
    {
         void execute(int time, TaskTimer taskTimer);
    }

    private TaskAction task;
    private Consumer<TaskTimer> onStop;
    private int speed =20;
    private int time = 0;
    private int runAfter = 0;
    private int stopAfter = 99999999;
    private  boolean isCancel = false;
    private  BukkitTask taskId ;
    public TaskTimer(int tick,TaskAction action)
    {
        this.speed = tick;
        this.task = action;
    }

    public void stop()
    {
        if(taskId!=null)
            taskId.cancel();
    }

    public void cancel()
    {
        isCancel =true;
    }

    public TaskTimer startAfter(int i)
    {
        this.runAfter = i;
        return this;
    }

    public TaskTimer stopAfter(int i)
    {
        this.stopAfter = i;
        return this;
    }
    public TaskTimer onStop(Consumer<TaskTimer> nextTask)
    {
        this.onStop = nextTask;
        return this;
    }


    public void runAgain()
    {
        this.time = 0;
        this.isCancel = false;
    }


    public void runAsync()
    {
        taskId= Bukkit.getScheduler().runTaskTimerAsynchronously(InicializerAPI.getPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(this.onStop!=null)
                    this.onStop.accept(null);

                stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
    }
    public void run()
    {
        taskId= Bukkit.getScheduler().runTaskTimer(InicializerAPI.getPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(this.onStop!=null)
                    this.onStop.accept(null);

                stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
    }

}