package jw.api.task;


public interface Task<T>
{
    public T execute(T args, TaskQueue taskPromise);
}
