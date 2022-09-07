package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;

public class TimeHandler implements GameHandler{
    private int time;

    public TimeHandler() {
        this.time = 0;
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }
}
