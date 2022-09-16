package me.ftsos.managers;

import me.ftsos.UHC;
import me.ftsos.game.UhcGamesManager;
import me.ftsos.listeners.ListenerManager;
import me.ftsos.utils.tasks.TaskManager;
import me.ftsos.utils.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public class ManagerHandler {
    private UHC plugin;
    private Map<Class<? extends Manager>, Manager> managers;
    public ManagerHandler(UHC plugin) {
        this.plugin = plugin;
        managers = new HashMap<>();
    }

    public void enable() {
        registerManagers();
    }

    public void disable() {
        unregisterManagers();
    }

    public <T extends Manager> T find(Class<T> clazz) {
        return clazz.cast(this.managers.get(clazz));
    }

    public void register(Manager manager) {
        this.managers.put(manager.getClass(), manager);
        manager.enable();
    }


    public void register(Class<? extends Manager> manager) {
        try {
            Manager managerInstance = manager.getDeclaredConstructor(UHC.class).newInstance(plugin);
            this.managers.put(manager, managerInstance);
            managerInstance.enable();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void unregister(Manager manager) {
        manager.disable();
        this.managers.remove(manager.getClass());
    }

    public void unregister(Class<? extends Manager> manager) {
        if(find(manager) == null) return;
        find(manager).disable();
        this.managers.remove(manager.getClass());
    }

    public void registerManagers() {
        register(new ConfigManager(plugin));
        register(new UhcGamesManager(plugin));
        register(new ListenerManager(plugin));
        register(new TaskManager(plugin));
    }

    public void unregisterManagers() {
        for(Map.Entry<Class<? extends Manager>, Manager> entry : managers.entrySet()) {
            unregister(entry.getKey());
        }
    }
}
