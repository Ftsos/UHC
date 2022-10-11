package me.ftsos.game;

import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;

public enum GameState {
    WAITING(Messages.GAMESTATE_WAITING_DISPLAYNAME),
    STARTING(Messages.GAMESTATE_STARTING_DISPLAYNAME),
    PLAYING(Messages.GAMESTATE_PLAYING_DISPLAYNAME),
    DEATHMATCH(Messages.GAMESTATE_DEATHMATCH_DISPLAYNAME),
    FINISHING(Messages.GAMESTATE_FINISHING_DISPLAYNAME),
    RESTARTING(Messages.GAMESTATE_RESTARTING_DISPLAYNAME);

    private String displayName;
    GameState(String displayName) {
        this.displayName = CC.colorize(displayName);
    }
    public String getDisplayName() {
        return CC.colorize(displayName);
    }
}
