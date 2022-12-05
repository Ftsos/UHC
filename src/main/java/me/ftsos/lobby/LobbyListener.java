package me.ftsos.lobby;

import me.ftsos.events.player.PlayerChangeWorldEvent;
import me.ftsos.events.player.PlayerFallIntoVoidEvent;
import me.ftsos.events.player.PlayerTakeDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener implements Listener {
    private LobbyManager lobbyManager;

    public LobbyListener(LobbyManager lobbyManager) {
        this.lobbyManager = lobbyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onPlayerFallIntoVoidEvent(PlayerFallIntoVoidEvent event) {
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onPlayerTakeDamage(PlayerTakeDamageEvent event) {
        if(event.isCancelled()) return;
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        if(event.isCancelled()) return;
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        lobbyManager.onEvent(event);
    }

    @EventHandler
    public void onWorldChange(PlayerChangeWorldEvent event) {
        lobbyManager.onEvent(event);
    }
}
