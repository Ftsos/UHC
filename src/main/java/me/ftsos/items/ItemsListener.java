package me.ftsos.items;

import me.ftsos.gui.GuiManager;
import me.ftsos.gui.impl.GamesGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsListener implements Listener {
    private GuiManager guiManager;

    public ItemsListener(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void openGamesItemLobbyListener(PlayerInteractEvent event) {
        if(!event.hasItem()) return;
        if(event.getPlayer() == null) return;
        if(!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        ItemStack itemThatClicked = event.getItem();
        if(!Items.OPEN_GAMES_ITEM_LOBBY.isSimilar(itemThatClicked)) return;
        this.guiManager.openGui(GamesGui.class, event.getPlayer());
    }
}
