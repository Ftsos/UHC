package me.ftsos.utils.tasks;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;
import me.ftsos.utils.Callback;

import java.util.HashMap;
import java.util.Map;

public class TaskManager extends Manager {
    private UHC plugin;
    private Map<Class<? extends Task>, Task> runnableMap;
    public TaskManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.runnableMap = new HashMap<>();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {
        unregisterTasks();
    }


    public <T extends Task> T find(Class<T> clazz) {
        return clazz.cast(this.runnableMap.get(clazz));
    }

    public void register(Task task, Callback callback) {
        this.runnableMap.put(task.getClass(), task);
        callback.run();
    }


    public void unregister(Task runnable, boolean cancel) {
        if(cancel) runnable.cancel();
        this.runnableMap.remove(runnable.getClass());
    }

    public void unregister(Task runnable, Callback callback) {
        callback.run();
        this.runnableMap.remove(runnable.getClass());
    }

    public void unregister(Class<? extends Task> runnableClazz, boolean cancel) {
        Task runnable = find(runnableClazz);
        if(runnable == null) return;
        unregister(runnableClazz, cancel);
    }

    public void unregister(Class<? extends Task> runnableClazz, Callback callback) {
        Task runnable = find(runnableClazz);
        if(runnable == null) return;
        unregister(runnableClazz, callback);
    }

    public void unregisterTasks() {
        for(Map.Entry<Class<? extends Task>, Task> entry : this.runnableMap.entrySet()) {
            unregister(entry.getKey(), true);
        }
    }
}

