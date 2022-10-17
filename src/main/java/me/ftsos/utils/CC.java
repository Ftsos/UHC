package me.ftsos.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CC {
    public static String colorize(String toColorize) {
        return ChatColor.translateAlternateColorCodes('&', toColorize);
    }
    public static void colorizeMessageToPlayer(Player player, String toColorize) {
        player.sendMessage(colorize(toColorize));
    }
    public static void colorizeMessageToPlayers(List<Player> players, String toColorize) {
        for(Player player : players) {
            colorizeMessageToPlayer(player, toColorize);
        }
    }

    public static void colorizeMessageListAndSendIt(Player player, List<String> lines) {
        for(String line : lines) {
            player.sendMessage(colorize(line));
        }
    }

    public static List<String> colorizeMessageList(List<String> lines){
        List<String> coloredLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            coloredLines.add(i, colorize(line));
        }
        return coloredLines;
    }

    public static List<String> colorizeMessageList(String ...lines){
        return colorizeMessageList(Arrays.asList(lines));
    }

    public static String[] colorizeMessageArray(String ...lines){
        List<String> coloredLines = colorizeMessageList(Arrays.asList(lines));
        return coloredLines.toArray(new String[coloredLines.size()]);
    }

}
