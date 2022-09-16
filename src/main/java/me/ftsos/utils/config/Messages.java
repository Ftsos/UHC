package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

public class Messages {
    private static final IYaml messages = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getMessages();
    /*
    * GameState Display Name Messages
    * */
    public static String GAMESTATE_WAITING_DISPLAYNAME = messages.getString("gamestates.waiting.displayName").orElse("&eWaiting");
    public static String GAMESTATE_STARTING_DISPLAYNAME = messages.getString("gamestates.starting.displayName").orElse("&6Starting");
    public static String GAMESTATE_PLAYING_DISPLAYNAME = messages.getString("gamestates.playing.displayName").orElse("&aPlaying");
    public static String GAMESTATE_DEATHMATCH_DISPLAYNAME = messages.getString("gamestates.deathmatch.displayName").orElse("&4Deathmatch");
    public static String GAMESTATE_FINISHING_DISPLAYNAME = messages.getString("gamestates.finishing.displayName").orElse("&cFinishing");
    public static String GAMESTATE_RESTARTING_DISPLAYNAME = messages.getString("gamestates.restarting.displayName").orElse("&4Restarting");

    /*
    * In Game Chats
    * */
    public static String NATURAL_PLAYER_DEATH_MESSAGE = messages.getString("gameplay.messages.naturalPlayerDeath").orElse("&c%player%&6 has dead because of %death_cause%");
    public static String PLAYER_KILL_MESSAGE = messages.getString("gameplay.messages.playerKill").orElse("&c%killer% &6killed &c%victim%");
    public static String STARTING_COUNTDOWN_NUMBER_MESSAGE = messages.getString("gameplay.messages.startingCountdownNumber").orElse("&eStarting on &c%timer%");

    /*
    * Player Title
    * */
    public static String STARTING_COUNTDOWN_NUMBER_TITLE = messages.getString("gameplay.titles.startingCountdownNumber").orElse("&e%timer%");

}
