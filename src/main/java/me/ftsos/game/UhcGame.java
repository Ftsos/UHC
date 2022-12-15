package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.handlers.*;
import me.ftsos.lobby.Lobby;
import me.ftsos.lobby.LobbyManager;
import me.ftsos.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class UhcGame {
    private GameState gameState;
    private GameTeamHandler gameTeamHandler;
    private GameOptions gameOptions;
    private MapHandler mapHandler;
    private GameListenerHandler gameListenerHandler;
    private TaskHandler taskHandler;
    private SpectatorHandler spectatorHandler;
    private TimeHandler timeHandler;
    private GamePlayerWrapperHandler gamePlayerWrapperHandler;
    private GameScoreboardHandler gameScoreboardHandler;
    private UHC plugin;

    public UhcGame(GameOptions options, UHC plugin) {
        this.plugin = plugin;
        this.gameOptions = options;
        this.taskHandler = new TaskHandler(plugin, this);
        this.gameListenerHandler = new GameListenerHandler(this);
        this.mapHandler = new MapHandler(plugin, this);
        this.gameState = GameState.WAITING;
        this.gameTeamHandler = new GameTeamHandler(this);
        this.spectatorHandler = new SpectatorHandler(this);
        this.timeHandler = new TimeHandler(this);
        this.gamePlayerWrapperHandler = new GamePlayerWrapperHandler(this);
        this.gameScoreboardHandler = new GameScoreboardHandler(this);
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
        this.gameTeamHandler.broadcastMessage(CC.colorize(message));
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

    public SpectatorHandler getSpectatorHandler() {
        return spectatorHandler;
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

    public Lobby getLobby() {
        return this.plugin.getManagerHandler().find(LobbyManager.class).getLobby();
    }

    public TimeHandler getTimeHandler() {
        return timeHandler;
    }

    public GamePlayerWrapperHandler getGamePlayerWrapperHandler() {
        return gamePlayerWrapperHandler;
    }

    public GameScoreboardHandler getGameScoreboardHandler() {
        return gameScoreboardHandler;
    }

    /*
    * This method will finish the game accordingly but forcefully, won't send fireworks or get a winner, but will automatically finish the game
    * and at some point will execute the function removeGame().
    * */
    public void forcefullyFinishGame() {
        this.updateGameState(GameState.RESTARTING);
    }

    /*
    * This method is only meant to be used when the game HAS ALREADY FINISHED, and just left to be removed, if you use this method
    * on a game that hasn't finished, the people in the game won't left, and map won't be erased, and in general more problems etc.
    * if you want to forcefully end the game use the forcefullyFinishGame() method, this method is only to remove games that are already
    * in the gamestate of restarting (i.e. which players are outside of lobby, scoreboards closed) this method will basically just remove a game
    * from the game list found in the manager class (UhcGamesManager). The forcefullyFinishGame() method at some point executes this method, no need to call again
    * */

    public void removeGame() {
        if(this.getGameState() != GameState.RESTARTING) return;
        this.plugin.getManagerHandler().find(UhcGamesManager.class).getUhcGamesManagerWrapper().removeUhcGame(this);
    }
}

