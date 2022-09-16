package me.ftsos.utils.tasks;

import me.ftsos.UHC;
import me.ftsos.utils.Callback;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Task {
    private BukkitRunnable runnable;
    private UHC plugin;
    private TaskManager taskManager;

    public Task(Callback onStartupCallback) {
        this.plugin = UHC.getPlugin();
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                runCustomTask();
            }
        };
        this.taskManager = this.plugin.getManagerHandler().find(TaskManager.class);
        this.taskManager.register(this, onStartupCallback);
    }

    public Task() {
        this.plugin = UHC.getPlugin();
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                runCustomTask();
            }
        };
        this.taskManager = this.plugin.getManagerHandler().find(TaskManager.class);
        this.taskManager.register(this, () -> {});
    }

    public abstract void runCustomTask();

    public void cancel() {
        this.runnable.cancel();
        this.taskManager.unregister(this, () -> {});
    }

    public void cancel(Callback onCancelingCallback) {
        this.runnable.cancel();
        this.taskManager.unregister(this, () -> {});
    }

    public void runTask() {
        this.runnable.runTask(this.plugin);
    }

    public void runTaskAsynchronously() {
        this.runnable.runTaskAsynchronously(this.plugin);
    }

    public void runTaskLater(long delay) {
        this.runnable.runTaskLater(this.plugin, delay);
    }

    public void runTaskLaterAsynchronously(long delay) {
        this.runnable.runTaskLaterAsynchronously(this.plugin, delay);
    }

    public void runTaskTimer(long delay, long period) {
        this.runnable.runTaskTimer(this.plugin, delay, period);
    }

    public void runTaskTimerAsynchronously(long delay, long period) {
        this.runnable.runTaskTimerAsynchronously(this.plugin, delay, period);
    }

    public int getTaskId() {
        return this.runnable.getTaskId();
    }

}
