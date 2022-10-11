package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import me.ftsos.UHC;
import org.bukkit.Material;

public class Inventories {
    private static IYaml inventories = UHC.getPlugin().getManagerHandler().find(ConfigManager.class).getInventories();

    /**
     * Handler of the inventories.yml file
    * */

     /*
     * Spectator Inventory
     * */

    /*
    * Spectator Inventories Materials
    * */
    public static Material LEAVE_ITEM_MATERIAL_SPECTATOR = Material.getMaterial(inventories.getString("spectator.leaveItem.material").orElse("BED")) == null ? Material.BED : Material.getMaterial(inventories.getString("spectator.leaveItem.material").orElse("BED"));
    public static Material TELEPORT_ITEM_MATERIAL_SPECTATOR = Material.getMaterial(inventories.getString("spectator.teleportItem.material").orElse("COMPASS")) == null ? Material.COMPASS : Material.getMaterial(inventories.getString("spectator.teleportItem.material").orElse("COMPASS"));
    public static Material PLAY_AGAIN_ITEM_MATERIAL_SPECTATOR = Material.getMaterial(inventories.getString("spectator.playAgainItem.material").orElse("BEACON")) == null ? Material.BEACON : Material.getMaterial(inventories.getString("spectator.playAgainItem.material").orElse("BEACON"));

    /*
     * Spectator Inventories Slots
     * */
    public static int LEAVE_ITEM_SLOT_SPECTATOR = inventories.getInt("spectator.leaveItem.slot");
    public static int TELEPORT_ITEM_SLOT_SPECTATOR = inventories.getInt("spectator.teleportItem.slot");
    public static int PLAY_AGAIN_ITEM_SLOT_SPECTATOR = inventories.getInt("spectator.playAgainItem.slot");

    /*
     * Spectator Inventories Names
     * */
    public static String LEAVE_ITEM_NAME_SPECTATOR = inventories.getString("spectator.leaveItem.name").orElse("&c&lLeave");
    public static String TELEPORT_ITEM_NAME_SPECTATOR = inventories.getString("spectator.teleportItem.name").orElse("&a&lTeleport");
    public static String PLAY_AGAIN_ITEM_NAME_SPECTATOR = inventories.getString("spectator.playAgainItem.name").orElse("&b&lPlay again");

    /*
     * Spectator Inventories Lore's
     * */
    public static String[] LEAVE_ITEM_LORE_SPECTATOR = inventories.getStringList("spectator.leaveItem.lore").isEmpty() ? new String[]{"&8Click for leaving to lobby."} : inventories.getStringList("spectator.leaveItem.lore").toArray(new String[inventories.getStringList("spectator.leaveItem.lore").size()]);
    public static String[] TELEPORT_ITEM_LORE_SPECTATOR = inventories.getStringList("spectator.teleportItem.lore").isEmpty() ? new String[]{"&8Click to open the teleport menu."} : inventories.getStringList("spectator.teleportItem.lore").toArray(new String[inventories.getStringList("spectator.teleportItem.lore").size()]);
    public static String[] PLAY_AGAIN_ITEM_LORE_SPECTATOR = inventories.getStringList("spectator.playAgainItem.lore").isEmpty() ? new String[]{"&8Click to send you into a new match."} : inventories.getStringList("spectator.playAgainItem.lore").toArray(new String[inventories.getStringList("spectator.playAgainItem.lore").size()]);

    /*
    *
    * Non Inventory Items
    *
    * */

    public static Material OPEN_GAMES_ITEM_MATERIAL_LOBBY = Material.getMaterial(inventories.getString("lobby.openGamesItem.material").orElse("COMPASS")) == null ? Material.COMPASS : Material.getMaterial(inventories.getString("lobby.openGamesItem.material").orElse("COMPASS"));
    public static int OPEN_GAMES_ITEM_SLOT_LOBBY = inventories.getInt("lobby.openGamesItem.slot");
    public static String OPEN_GAMES_ITEM_NAME_LOBBY = inventories.getString("lobby.openGamesItem.name").orElse("&aGames");

}
