package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

import java.util.List;

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
    public static String CANT_PLACE_BLOCKS_MESSAGE = messages.getString("gameplay.messages.cantPlaceBlocks").orElse("&cYou can't place blocks here");
    public static String CANT_BREAK_BLOCKS_MESSAGE = messages.getString("gameplay.messages.cantBreakBlocks").orElse("&cYou can't break blocks here");
    public static String LOBBY_IS_FULL_MESSAGE = messages.getString("gameplay.messages.lobbyIsFull").orElse("&cLobby is already full");
    public static String PARTY_IS_TOO_BIG_FOR_LOBBY_MESSAGE = messages.getString("gameplay.messages.partyTooBigForLobby").orElse("&cYour party is too big for the lobby, you can't join");
    public static String GAME_HAS_ALREADY_STARTED_JOINING_SPECTATOR_MODE_MESSAGE = messages.getString("gameplay.messages.gameHasAlreadyStartedJoiningSpectatorMode").orElse("&cGame has already started, you are joining in &6Spectator &8mode");
    public static String GAME_HAS_ALREADY_STARTED_CANT_JOIN_SPECTATOR_MODE_MESSAGE = messages.getString("gameplay.messages.gameHasAlreadyStartedCantJoinSpectatorMode").orElse("&cGame has already started, but as a party you can't spectate");
    public static String JOINING_TO_A_GAME_MESSAGE = messages.getString("gameplay.messages.joiningToAGame").orElse("&aJoining you to &6%gameName%");
    public static String GAME_TEAM_WON_MESSAGE = messages.getString("gameplay.messages.gameTeamWon").orElse("&6Congrats &8to %gamePlayerNames% &8for winning this game");
    public static String TIED_GAME_MESSAGE = messages.getString("gameplay.messages.tiedGame").orElse("&cTie! &8No one was able to claim the victory, ending game");
    public static String GAME_FINISHED_TELEPORTING_TO_LOBBY_MESSAGE = messages.getString("gameplay.messages.gameFinishedTeleportingToLobby").orElse("&aThe game has ended, teleporting you back to the lobby");

    /*
    * In Game Formats
    * */
    public static String GAME_TEAM_WON_GAME_PLAYER_NAME_DISPLAY_CONCATENATOR_FORMAT = messages.getString("gameplay.formats.gameTeamWonGamePlayerNameDisplayConcatenator").orElse("&8, ");
    public static String GAME_TEAM_WON_GAME_PLAYER_NAME_DISPLAY_FORMAT = messages.getString("gameplay.formats.gameTeamWonGamePlayerNameDisplayFormat").orElse("&6%gamePlayerName%");
    public static String GAME_TEAM_WON_OFFLINE_GAME_PLAYER_NAME_DISPLAY_FORMAT = messages.getString("gameplay.formats.gameTeamWonOfflineGamePlayerNameDisplayFormat").orElse("&c&o(Offline) &r&%gamePlayerName%");


    /*
    * In Game Player Title
    * */
    public static String STARTING_COUNTDOWN_NUMBER_TITLE = messages.getString("gameplay.titles.startingCountdownNumber").orElse("&e%timer%");

    /*
    * Commands
    * */
    public static List<String> UHC_INFO_SUB_COMMAND_MESSAGE = messages.getStringList("commands.uhc.infoSubCommand");
    public static String UHC_START_SUB_COMMAND_MESSAGE = messages.getString("commands.uhc.startSubCommand.message").orElse("&aStarting new game: &6%gameName%");
    public static String UHC_START_SUB_COMMAND_ANNOUNCE_MESSAGE = messages.getString("commands.uhc.startSubCommand.announceMessage").orElse("&a&lNew Game Starting! &6&l%gameName%");
    public static String UHC_JOIN_SUB_COMMAND_GAME_DOESNT_EXIST_MESSAGE = messages.getString("commands.uhc.joinSubCommand.gameDoesntExist").orElse("&cThe game \"%gameName%\" doesnt exist");
    public static String UHC_FORCE_STOP_SUB_COMMAND_GAME_DOESNT_EXIST_MESSAGE = messages.getString("commands.uhc.forceStopSubCommand.gameDoesntExist").orElse("&cThe game \"%gameName%\" doesn't exist");
    public static String UHC_FORCE_STOP_SUB_COMMAND_GAME_STOPPED_MESSAGE = messages.getString("commands.uhc.forceStopSubCommand.gameStopped").orElse("&cThe game &6%gameName% &chas been stopped");
    /*
    * Utils
    * */
    public static String NO_PERMISSION_MESSAGE = messages.getString("noPermission").orElse("&cYou don't have the permission to do that action");
}
