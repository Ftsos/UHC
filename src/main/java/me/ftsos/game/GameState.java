package me.ftsos.game;

import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import me.ftsos.utils.config.Scoreboards;

import java.util.List;

public enum GameState {
    WAITING(Messages.GAMESTATE_WAITING_DISPLAYNAME, Scoreboards.WAITING_GAME_STATE_SCOREBOARD, Scoreboards.WAITING_GAME_STATE_SCOREBOARD_TITLE),
    STARTING(Messages.GAMESTATE_STARTING_DISPLAYNAME, Scoreboards.STARTING_GAME_STATE_SCOREBOARD, Scoreboards.STARTING_GAME_STATE_SCOREBOARD_TITLE),
    PLAYING(Messages.GAMESTATE_PLAYING_DISPLAYNAME, Scoreboards.PLAYING_GAME_STATE_SCOREBOARD, Scoreboards.PLAYING_GAME_STATE_SCOREBOARD_TITLE),
    DEATHMATCH(Messages.GAMESTATE_DEATHMATCH_DISPLAYNAME, Scoreboards.DEATHMATCH_GAME_STATE_SCOREBOARD, Scoreboards.DEATHMATCH_GAME_STATE_SCOREBOARD_TITLE),
    FINISHING(Messages.GAMESTATE_FINISHING_DISPLAYNAME, Scoreboards.FINISHING_GAME_STATE_SCOREBOARD, Scoreboards.FINISHING_GAME_STATE_SCOREBOARD_TITLE),
    RESTARTING(Messages.GAMESTATE_RESTARTING_DISPLAYNAME, Scoreboards.RESTARTING_GAME_STATE_SCOREBOARD, Scoreboards.RESTARTING_GAME_STATE_SCOREBOARD_TITLE);

    private String displayName;
    private List<String> scoreboard;
    private String scoreboardTitle;

    GameState(String displayName, List<String> scoreboard, String scoreboardTitle) {
        this.displayName = CC.colorize(displayName);
        this.scoreboard = scoreboard;
        this.scoreboardTitle = CC.colorize(scoreboardTitle);
    }

    public String getDisplayName() {
        return CC.colorize(displayName);
    }

    public String getScoreboardTitle() {
        return scoreboardTitle;
    }

    public List<String> getScoreboard() {
        return scoreboard;
    }
}
