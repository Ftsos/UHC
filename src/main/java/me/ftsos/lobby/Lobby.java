package me.ftsos.lobby;

import me.ftsos.lobby.handlers.LobbyPlayersHandler;
import me.ftsos.lobby.handlers.LobbyWorldHandler;
import org.bukkit.event.Event;

public class Lobby {
    private LobbyWorldHandler lobbyWorldHandler;
    private LobbyPlayersHandler lobbyPlayersHandler;
    public Lobby() {
        this.lobbyWorldHandler = new LobbyWorldHandler();
        this.lobbyPlayersHandler = new LobbyPlayersHandler(this);
    }

    public void onEvent(Event event) {
        lobbyWorldHandler.onEvent(event);
        lobbyPlayersHandler.onEvent(event);
    }

    public LobbyWorldHandler getLobbyWorldHandler() {
        return lobbyWorldHandler;
    }

    public LobbyPlayersHandler getLobbyPlayersHandler() {
        return lobbyPlayersHandler;
    }
}
