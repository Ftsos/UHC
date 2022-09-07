package me.ftsos.managers;

import me.ftsos.UHC;

public abstract class Manager {
    private UHC plugin;

    public Manager(UHC plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();
    public abstract void disable();
}
