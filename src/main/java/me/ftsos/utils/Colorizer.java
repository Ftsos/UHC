package me.ftsos.utils;

import org.bukkit.ChatColor;

public class Colorizer {
    public static String colorize(String toColorize) {
        return ChatColor.translateAlternateColorCodes('&', toColorize);
    }
}
