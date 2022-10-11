package me.ftsos.items;

import me.ftsos.utils.CC;
import me.ftsos.utils.ItemBuilder;
import me.ftsos.utils.config.Inventories;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Items {
    /**
     * A class dedicated to provide static references to all items in the UhcGame
     */

    /*
    * Game Spectator Items
    */
    public static ItemStack LEAVE_ITEM_SPECTATOR_MENU = new ItemBuilder(Inventories.LEAVE_ITEM_MATERIAL_SPECTATOR).setName(CC.colorize(Inventories.LEAVE_ITEM_NAME_SPECTATOR)).setLore(CC.colorizeMessageList(Inventories.LEAVE_ITEM_LORE_SPECTATOR)).toItemStack();
    public static ItemStack TELEPORT_ITEM_SPECTATOR_MENU = new ItemBuilder(Inventories.TELEPORT_ITEM_MATERIAL_SPECTATOR).setName(CC.colorize(Inventories.TELEPORT_ITEM_NAME_SPECTATOR)).setLore(CC.colorizeMessageList(Inventories.TELEPORT_ITEM_LORE_SPECTATOR)).toItemStack();
    public static ItemStack PLAY_AGAIN_ITEM_SPECTATOR_MENU = new ItemBuilder(Inventories.PLAY_AGAIN_ITEM_MATERIAL_SPECTATOR).setName(CC.colorize(Inventories.PLAY_AGAIN_ITEM_NAME_SPECTATOR)).setLore(CC.colorizeMessageList(Inventories.PLAY_AGAIN_ITEM_LORE_SPECTATOR)).toItemStack();

    /*
    * Lobby Items
    * */
    public static ItemStack OPEN_GAMES_ITEM_LOBBY = new ItemBuilder(Inventories.OPEN_GAMES_ITEM_MATERIAL_LOBBY).setName(CC.colorize(Inventories.OPEN_GAMES_ITEM_NAME_LOBBY)).toItemStack();
}
