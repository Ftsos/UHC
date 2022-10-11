package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

public class Config {
    private static IYaml config = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getConfig();

    public static int SPAWN_RADIUS = config.getInt("spawnRadius");
    public static int STARTING_COUNTDOWN_TIMER = config.getInt("startingCountdownTimer");
    public static String LOBBY_WORLD_NAME = config.getString("lobby.lobbyWorldName").orElse("lobby");
    public static double LOBBY_SPAWN_X_COORD = config.getSection("").getDouble("lobby.spawn.x");
    public static double LOBBY_SPAWN_Y_COORD = config.getSection("").getDouble("lobby.spawn.y");
    public static double LOBBY_SPAWN_Z_COORD = config.getSection("").getDouble("lobby.spawn.z");
    public static float LOBBY_SPAWN_YAW_COORD = (float) config.getSection("").getDouble("lobby.spawn.yaw");
    public static float LOBBY_SPAWN_PITCH_COORD = (float) config.getSection("").getDouble("lobby.spawn.pitch");
}
