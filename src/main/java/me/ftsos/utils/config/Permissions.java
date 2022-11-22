package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

public class Permissions {
    private static IYaml permissions = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getPermissions();

    /*
    * Commands
    * */

    /*
    * Uhc Command, Uhc Sub Commands & Related Things
    * */
    public static final String UHC_BASE_COMMAND_PERMISSION = permissions.getString("commands.uhc.basePermission").orElse("uhc.command");
    public static final String UHC_START_SUB_COMMAND_PERMISSION = permissions.getString("commands.uhc.subCommands.start").orElse("uhc.command.start");
    public static final String UHC_INFO_SUB_COMMAND_PERMISSION = permissions.getString("commands.uhc.subCommands.info").orElse("uhc.command.info");
    public static final String UHC_JOIN_SUB_COMMAND_PERMISSION = permissions.getString("commands.uhc.subCommands.join").orElse("uhc.command.join");
    public static final String UHC_FORCE_STOP_SUB_COMMAND_PERMISSION = permissions.getString("commands.uhc.subCommands.forceStop").orElse("uhc.command.forceStop");

    /*
    * Actions (Breaking/Placing blocks, etc)
    * */

    /*
    * Lobby Permission
    * */
    public static String LOBBY_BUILDING_OR_BREAKING_ON_LOBBY_PERMISSION = permissions.getString("lobby.buildingOrBreakingOnLobbyPermission").orElse("uhc.buildingOrBreakingOnLobby");

}
