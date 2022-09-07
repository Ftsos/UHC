package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;

public interface GameHandler {
    void onGameStateUpdate(GameStateUpdateEvent event);
}
