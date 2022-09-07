package me.ftsos.listeners;

import me.ftsos.UHC;
import me.ftsos.game.GameListener;
import me.ftsos.game.UhcGamesManager;
import me.ftsos.managers.Manager;
import org.bukkit.plugin.PluginManager;

public class ListenerManager extends Manager {
    private UHC plugin;

    public ListenerManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        registerListeners();
    }

    @Override
    public void disable() {

    }

    public void registerListeners() {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(new MainListener(), this.plugin);
        pm.registerEvents(new GameListener(this.plugin.getManagerHandler().find(UhcGamesManager.class)), this.plugin);
    }
}
