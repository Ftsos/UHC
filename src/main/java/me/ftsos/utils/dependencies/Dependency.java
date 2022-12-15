package me.ftsos.utils.dependencies;

import org.bukkit.plugin.Plugin;

public abstract class Dependency {

    public abstract boolean isNecessary();
    public abstract boolean isActive();
    public abstract Plugin getDependencyPlugin();
    public abstract void enableDependency();
    public abstract void disableDependency();
}
