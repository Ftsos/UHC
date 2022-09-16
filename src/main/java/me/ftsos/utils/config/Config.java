package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;

public class Config {
    private static IYaml config = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getConfig();

    public static int SPAWN_RADIUS = config.getInt("spawnRadius");
    public static int STARTING_COUNTDOWN_TIMER = config.getInt("startingCountdownTimer");

}
