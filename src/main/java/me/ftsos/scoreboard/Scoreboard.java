package me.ftsos.scoreboard;

import dev.jcsoftware.jscoreboards.JScoreboard;

public interface Scoreboard<T extends JScoreboard> {
    /*
    * TODO:
    * 1. Finish Scoreboard Setup
    * 2. Add schematics helper & support using this utility https://www.spigotmc.org/threads/worldedit-utilities-modify-paste-schematics.275100/
    * */

    T getScoreboard();

    default ScoreboardInstanceType getScoreboardInstanceType() {
        return ScoreboardInstanceType.getInstanceType(this.getScoreboard());
    }

}