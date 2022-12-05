package me.ftsos.scoreboard;

import dev.jcsoftware.jscoreboards.JGlobalScoreboard;
import dev.jcsoftware.jscoreboards.JPerPlayerScoreboard;
import dev.jcsoftware.jscoreboards.JScoreboard;

public enum ScoreboardInstanceType {
    GLOBAL_SCOREBOARD(JGlobalScoreboard.class),
    PER_PLAYER_SCOREBOARD(JPerPlayerScoreboard.class);


    private Class<? extends JScoreboard> type;

    ScoreboardInstanceType(Class<? extends JScoreboard> guiClass) {
        this.type = guiClass;
    }

    public Class<? extends JScoreboard> getType() {
        return type;
    }

    public boolean isSameType(Class<? extends JScoreboard> clazz) {
        if(this.type == clazz) return true;
        return false;
    }

    public <T extends JScoreboard> boolean isSameType(T scoreboard) {
        if(type.isInstance(scoreboard)) return true;
        return false;
    }

    public static <T extends JScoreboard> ScoreboardInstanceType getInstanceType(T scoreboard) {
        for(ScoreboardInstanceType respectiveType : ScoreboardInstanceType.values()) {
            if(respectiveType.isSameType(scoreboard)) return respectiveType;
        }
        return null;
    }
}
