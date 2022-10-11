package me.ftsos.lobby;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;
import org.bukkit.event.Event;

public class LobbyManager extends Manager {
    private UHC plugin;
    private Lobby lobby;
    public LobbyManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.lobby = new Lobby();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void onEvent(Event event) {
        lobby.onEvent(event);
    }

    public Lobby getLobby() {
        return lobby;
    }
}
