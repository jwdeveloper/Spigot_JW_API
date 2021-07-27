package jw.task;

import jw.InicializerAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TaskQueue<T> {
    private ArrayList<Task<T>> tasks = new ArrayList<>();
    private Consumer<Exception> onerror = (e) -> {
    };
    private BukkitTask bukkitTask;
    private T obj;
    private boolean is_cancel = false;

    public TaskQueue(Task<T> task) {
        this.tasks.add(task);
    }


    public void ThrowError(Exception e) {
        this.Cancel();
        onerror.accept(e);
    }


    public TaskQueue Then(Task<T> tasks) {
        this.tasks.add(tasks);
        return this;
    }

    public TaskQueue OnError(Consumer<Exception> onerror) {
        this.onerror = onerror;
        return this;
    }

    public void Cancel() {
        is_cancel = true;
        bukkitTask.cancel();
    }

    public void Run(T obj) {
        this.obj = obj;
        Run();
    }

    public void Run() {
        is_cancel = false;
        bukkitTask = Bukkit.getScheduler().runTaskAsynchronously(InicializerAPI.getPlugin(), () ->
        {
            try {
                T result = obj;
                Task<T> current = null;
                for (int i = 0; i < tasks.size(); i++) {
                    current = tasks.get(i);
                    result = current.execute(result, this);

                    if (this.is_cancel)
                        break;
                }

            } catch (Exception e) {
                onerror.accept(e);
            }
        });

    }


    public void RunSync() {
        is_cancel = false;
        bukkitTask = Bukkit.getScheduler().runTaskAsynchronously(InicializerAPI.getPlugin(), () ->
        {
            try {
                AtomicReference<T> result = new AtomicReference<>(tasks.get(0).execute(obj, this));

                if (tasks.size() > 1) {
                    Bukkit.getScheduler().runTask(InicializerAPI.getPlugin(), () ->
                    {
                        try {
                            Task<T> current = null;
                            for (int i = 1; i < tasks.size(); i++) {
                                current = tasks.get(i);
                                result.set(current.execute(result.get(), this));

                                if (this.is_cancel)
                                    break;
                            }
                        } catch (Exception e) {
                            onerror.accept(e);
                        }
                    });
                }
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(InicializerAPI.getPlugin(), () ->
                {
                    onerror.accept(e);
                });
            }
        });
    }
}
