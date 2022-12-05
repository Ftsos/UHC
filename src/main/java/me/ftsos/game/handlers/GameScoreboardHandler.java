package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.utils.GameTask;
import me.ftsos.scoreboard.impl.GameStateScoreboard;
import org.bukkit.entity.Player;

public class GameScoreboardHandler implements GameHandler{
    private UhcGame uhcGame;
    private GameStateScoreboard scoreboard;
    private GameTask updateScoreboardTask;

    public GameScoreboardHandler(UhcGame uhcGame) {
        this.uhcGame = uhcGame;
        this.scoreboard = new GameStateScoreboard(this.uhcGame, this.uhcGame.getGameState());
        this.updateScoreboardTask = new GameTask(this.uhcGame) {
            @Override
            public void runCustomTask() {
                scoreboard.updateScoreboard();
            }
        };
        this.updateScoreboardTask.runTaskTimer(0L, 5L);
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        this.scoreboard.destroyScoreboard();
        this.scoreboard = null;
        if(event.getNewGameState() == GameState.RESTARTING) {
            this.updateScoreboardTask.cancel();
            return;
        }
        this.scoreboard = new GameStateScoreboard(this.uhcGame, event.getNewGameState());
    }

    public void onPlayerJoinScoreboardGame(Player player) {
        this.scoreboard.playerJoin(player);
    }

    public void onPlayerLeaveScoreboardGame(Player player) {
        this.scoreboard.playerLeave(player);
    }
}
