package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

import java.util.List;

public class Scoreboards {
    private static final IYaml scoreboards = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getScoreboards();

    /*
    * Lobby Game State Scoreboard
    * */
    //Waiting
    public static String WAITING_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("waitingGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> WAITING_GAME_STATE_SCOREBOARD = scoreboards.getStringList("waitingGameStateScoreboard");
    //Starting
    public static String STARTING_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("startingGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> STARTING_GAME_STATE_SCOREBOARD = scoreboards.getStringList("startingGameStateScoreboard");
    //Playing
    public static String PLAYING_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("playingGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> PLAYING_GAME_STATE_SCOREBOARD = scoreboards.getStringList("playingGameStateScoreboard");
    //Deathmatch
    public static String DEATHMATCH_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("deathmatchGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> DEATHMATCH_GAME_STATE_SCOREBOARD = scoreboards.getStringList("deathmatchGameStateScoreboard");
    //Finishing
    public static String FINISHING_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("finishingGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> FINISHING_GAME_STATE_SCOREBOARD = scoreboards.getStringList("finishingGameStateScoreboard");
    //Restarting
    public static String RESTARTING_GAME_STATE_SCOREBOARD_TITLE = scoreboards.getString("restartingGameStateScoreboardTitle").orElse("&6UHC");
    public static List<String> RESTARTING_GAME_STATE_SCOREBOARD = scoreboards.getStringList("restartingGameStateScoreboard");
}
