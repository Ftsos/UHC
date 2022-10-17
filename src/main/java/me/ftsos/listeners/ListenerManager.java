package me.ftsos.listeners;

import me.ftsos.UHC;
import me.ftsos.game.GameListener;
import me.ftsos.game.UhcGamesManager;
import me.ftsos.gui.GuiManager;
import me.ftsos.items.ItemsListener;
import me.ftsos.lobby.LobbyListener;
import me.ftsos.lobby.LobbyManager;
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
        pm.registerEvents(new LobbyListener(this.plugin.getManagerHandler().find(LobbyManager.class)), this.plugin);
        pm.registerEvents(new ItemsListener(this.plugin.getManagerHandler().find(GuiManager.class)), this.plugin);
    }
}
