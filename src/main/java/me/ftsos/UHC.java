package me.ftsos;

import me.ftsos.managers.ManagerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class UHC extends JavaPlugin {
    private ManagerHandler managerHandler;
    private static UHC uhc;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.managerHandler = new ManagerHandler(this);
        getManagerHandler().enable();
        this.uhc = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getManagerHandler().disable();
        this.uhc = null;
    }

    public ManagerHandler getManagerHandler() {
        return managerHandler;
    }

    public static UHC getPlugin() {
        return uhc;
    }
}
