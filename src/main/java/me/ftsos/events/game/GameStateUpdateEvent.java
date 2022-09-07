package me.ftsos.events.game;

import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateUpdateEvent extends Event implements Cancellable {
    private GameState oldGameState;
    private GameState newGameState;
    private UhcGame game;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public GameStateUpdateEvent(GameState oldGameState, GameState newGameState, UhcGame uhcGame) {
        this.oldGameState = oldGameState;
        this.newGameState = newGameState;
        this.game = uhcGame;
        this.isCancelled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public GameState getOldGameState() {
        return oldGameState;
    }

    public GameState getNewGameState() {
        return newGameState;
    }

    public void setOldGameState(GameState oldGameState) {
        this.oldGameState = oldGameState;
    }

    public void setNewGameState(GameState newGameState) {
        this.newGameState = newGameState;
    }

    public UhcGame getGame() {
        return game;
    }
}
