package jw.api.task;


import jw.api.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class TaskTimer
{
    public interface  I_Task
    {
         void execute(int time, TaskTimer taskTimer);
    }

    I_Task task;

    private Consumer<TaskTimer> onstop;
    private int speed =20;
    private int time = 0;
    private int stopAfter = 99999999;
    private int runAfter = 0;
    private  boolean isCancel = false;
    private  BukkitTask taskId ;
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
        isCancel =true;
    }
    public TaskTimer StartAfter(int i)
    {
        this.runAfter = i;
        return this;
    }

    public TaskTimer StopAfter(int i)
    {
        this.stopAfter = i;
        return this;
    }
    public TaskTimer OnStop(Consumer<TaskTimer> task)
    {
        this.onstop = task;
        return this;
    }


    public void RunAgain()
    {
        this.time = 0;
        this.isCancel = false;
    }


    public void RunAsync()
    {
        taskId= Bukkit.getScheduler().runTaskTimerAsynchronously(InicializerAPI.GetPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(this.onstop!=null)
                    this.onstop.accept(null);

                Stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
    }
    public void Run()
    {
        taskId= Bukkit.getScheduler().runTaskTimer(InicializerAPI.GetPlugin(),()->
        {
            if(time>=stopAfter || isCancel)
            {
                if(this.onstop!=null)
                    this.onstop.accept(null);

                Stop();
                return;
            }
            task.execute(time,this);
            time++;
        },runAfter,speed);
    }

}