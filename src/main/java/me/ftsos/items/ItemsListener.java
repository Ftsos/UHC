package me.ftsos.items;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemsListener implements Listener {
    //TODO: Finish method, and add GUI with library and register the Class
    @EventHandler
    public void openGamesItemLobbyListener(PlayerInteractEvent event) {
        if(!event.hasItem()) return;
        if(event.getPlayer() == null) return;
        ItemStack itemThatClicked = event.getItem();
        if(itemThatClicked != Items.OPEN_GAMES_ITEM_LOBBY) return;

    }
}
