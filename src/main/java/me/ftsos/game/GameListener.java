package me.ftsos.game;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.events.player.*;
import me.ftsos.events.game.UhcGamePlayerDeathEvent;
import me.ftsos.game.players.GameSpectator;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {
    private UhcGamesManager uhcGamesManager;

    public GameListener(UhcGamesManager uhcGamesManager) {
        this.uhcGamesManager = uhcGamesManager;
    }

    /*
    * Game Listeners
    * */

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onPlayerHitEntityEvent(PlayerHitEntityEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);
    }

    @EventHandler
    public void onPlayerGetsHitByEntityEvent(PlayerGetsHitByEntityEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);
    }

    @EventHandler
    public void onPlayerHitPlayerEvent(PlayerHitPlayerEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onPlayerTakeDamage(PlayerTakeDamageEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.isCancelled()) return;
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onPlayerFallIntoVoid(PlayerFallIntoVoidEvent event) {
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        uhcGamesManager.getUhcGamesManagerWrapper().onEvent(event);

    }

    /*
    * Players Game Listeners
    * */

    @EventHandler
    public void onGamePlayerDeath(UhcGamePlayerDeathEvent event) {
        if(event.getGame().getGameState() == GameState.WAITING ||
                event.getGame().getGameState() == GameState.STARTING ||
                event.getGame().getGameState() == GameState.FINISHING ||
                event.getGame().getGameState() == GameState.RESTARTING) return;

        String deathMessage = "";
        if(event.getKiller() == null) {
            deathMessage = Messages.NATURAL_PLAYER_DEATH_MESSAGE.replace("%player%", Bukkit.getOfflinePlayer(event.getVictim().getPlayerUUID()).getName()).replace("%death_cause%", event.getCause().name());
        } else {
            deathMessage = Messages.PLAYER_KILL_MESSAGE.replace("%killer%", Bukkit.getOfflinePlayer(event.getKiller().getPlayerUUID()).getName()).replace("%victim%", Bukkit.getOfflinePlayer(event.getVictim().getPlayerUUID()).getName());
        }
        event.getGame().broadcastMessage(deathMessage);

        event.getVictim().getPlayer().ifPresent(player -> event.getGame().getGameTeamHandler().onPlayerGetKilled(event.getTeam(), event.getVictim()));
    }

    /*
    * GameStates Updates Listener
    * */
    @EventHandler
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;

        UhcGame game = event.getGame();

        game.getMapHandler().onGameStateUpdate(event);
        game.getGameTeamHandler().onGameStateUpdate(event);
        game.getGamePlayerWrapperHandler().onGameStateUpdate(event);
        game.getGameListenerHandler().onGameStateUpdate(event);
        game.getSpectatorHandler().onGameStateUpdate(event);
        game.getTimeHandler().onGameStateUpdate(event);
        game.getTaskHandler().onGameStateUpdate(event);
        game.getGameScoreboardHandler().onGameStateUpdate(event);

        if(event.getNewGameState() == GameState.RESTARTING) game.removeGame();
    }
}