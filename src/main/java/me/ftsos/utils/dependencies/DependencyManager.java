package me.ftsos.utils.dependencies;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;
import me.ftsos.utils.CC;
import me.ftsos.utils.dependencies.worldedit.WorldEditDependency;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class DependencyManager extends Manager {
    private UHC plugin;
    private Map<Class<? extends Dependency>, Dependency> dependencies;
    public DependencyManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.dependencies = new HashMap<>();
    }

    @Override
    public void enable() {
        registerDependencies();
    }

    @Override
    public void disable() {
        unregisterDependencies();
    }

    public <T extends Dependency> T find(Class<T> clazz) {
        return clazz.cast(this.dependencies.get(clazz));
    }

    public void register(Dependency dependency) {
        this.dependencies.put(dependency.getClass(), dependency);
        dependency.enableDependency();
        if(dependency.isNecessary() && !dependency.isActive()) {
            Bukkit.getLogger().severe(CC.colorize("&cA dependency that was necessary was not able to load, disabling plugin"));
            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }


    public void unregister(Dependency dependency) {
        dependency.disableDependency();
        this.dependencies.remove(dependency.getClass());
    }

    public void unregister(Class<? extends Dependency> dependency) {
        if(find(dependency) == null) return;
        find(dependency).disableDependency();
        this.dependencies.remove(dependency);
    }

    public void registerDependencies() {
        register(new WorldEditDependency(this.plugin));
    }

    public void unregisterDependencies() {
        for(Map.Entry<Class<? extends Dependency>, Dependency> entry : this.dependencies.entrySet()) {
            unregister(entry.getKey());
        }
    }
}
