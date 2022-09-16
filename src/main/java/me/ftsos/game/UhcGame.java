package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.handlers.GameListenerHandler;
import me.ftsos.game.handlers.GameTeamHandler;
import me.ftsos.game.handlers.MapHandler;
import me.ftsos.game.handlers.TaskHandler;
import me.ftsos.utils.Colorizer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class UhcGame {
    private GameState gameState;
    private GameTeamHandler gameTeamHandler;
    private GameOptions gameOptions;
    private MapHandler mapHandler;
    private GameListenerHandler gameListenerHandler;
    private TaskHandler taskHandler;
    private UHC plugin;

    public UhcGame(GameOptions options, UHC plugin) {
        this.plugin = plugin;
        this.gameOptions = options;
        this.taskHandler = new TaskHandler(plugin, this);
        this.gameListenerHandler = new GameListenerHandler(this);
        this.mapHandler = new MapHandler();
        this.gameState = GameState.WAITING;
        this.gameTeamHandler = new GameTeamHandler(this);
        this.updateGameState(GameState.WAITING);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void updateGameState(GameState gameState) {
        GameStateUpdateEvent gameStateUpdateEvent = new GameStateUpdateEvent(this.gameState, gameState, this);
        Bukkit.getPluginManager().callEvent(gameStateUpdateEvent);

        if(gameStateUpdateEvent.isCancelled()) {
            return;
        }

        this.gameState = gameState;

    }

    public void broadcastMessage(String message) {
        this.gameTeamHandler.broadcastMessage(Colorizer.colorize(message));
    }

    public GameTeamHandler getGameTeamHandler() {
        return gameTeamHandler;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public void onEvent(Event event) {
        this.getGameListenerHandler().onEvent(event);
    }

    public GameListenerHandler getGameListenerHandler() {
        return gameListenerHandler;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }
}

