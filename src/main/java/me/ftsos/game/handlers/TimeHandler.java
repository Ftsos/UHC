package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.utils.GameTask;

public class TimeHandler implements GameHandler {
    private int time;
    private UhcGame game;
    private GameTask task;
    public TimeHandler(UhcGame game) {
        this.time = 0;
        this.game = game;
        this.task = new GameTask(this.game) {

            @Override
            public void runCustomTask() {
                time++;
            }
        };

    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;
        if(event.getNewGameState() == GameState.PLAYING) this.task.runTaskTimer(0L, 20L);
        if(event.getNewGameState() == GameState.FINISHING) this.task.cancel();
    }

    public int getTime() {
        return time;
    }
}
