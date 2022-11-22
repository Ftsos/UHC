package me.ftsos.game.handlers;

import me.ftsos.UHC;
import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.utils.Callback;
import me.ftsos.utils.tasks.Task;
import me.ftsos.utils.tasks.TaskManager;

import java.util.HashMap;
import java.util.Map;

public class TaskHandler implements GameHandler{
    private TaskManager taskManager;
    private UhcGame game;
    private Map<Class<? extends Task>, Task> localRunnableMap;

    /**
     * @TaskHandler
     * A simple handler to the general Task Manager
     * */
    public TaskHandler(UHC plugin, UhcGame game) {
        this.taskManager = plugin.getManagerHandler().find(TaskManager.class);
        this.game = game;
        this.localRunnableMap = new HashMap<>();
    }

    /*
    * Unregister all tasks if the game is ending
    * */
    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;
        if(event.getNewGameState() != GameState.RESTARTING) return;
        unregisterTasks();
    }

    private void unregisterTasks() {
        for(Map.Entry<Class<? extends Task>, Task> entry : this.localRunnableMap.entrySet()) {
            unregister(entry.getKey(), true);
        }
    }


    public <T extends Task> T get(Class<T> clazz) {
        return this.taskManager.find(clazz);
    }

    public void register(Task runnable, Callback callback) {
        this.taskManager.register(runnable, callback);
        this.localRunnableMap.put(runnable.getClass(), runnable);
    }

    public void unregister(Task runnable, boolean cancel) {
        this.taskManager.unregister(runnable, cancel);
        this.localRunnableMap.remove(runnable.getClass());
    }

    public void unregister(Task runnable, Callback callback) {
        this.taskManager.unregister(runnable, callback);
        this.localRunnableMap.remove(runnable.getClass());
    }

    public void unregister(Class<? extends Task> runnableClazz, boolean cancel) {
        Task runnable = get(runnableClazz);
        if(runnable == null) return;
        unregister(runnable, cancel);
    }

    public void unregister(Class<? extends Task> runnableClazz, Callback callback) {
        Task runnable = get(runnableClazz);
        if(runnable == null) return;
        unregister(runnable, callback);
    }

}

