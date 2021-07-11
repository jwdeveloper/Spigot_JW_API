package jw.api.task;

import java.util.ArrayList;
import java.util.TimerTask;

public class TaskManager
{


    public ArrayList<TimerTask> tasks = new ArrayList<>();



    public void AddTask()
    {

    }

    public void StopTasks()
    {
        for(int i=0;i<tasks.size();i++)
        {
            tasks.get(i).cancel();
        }
    }

}
