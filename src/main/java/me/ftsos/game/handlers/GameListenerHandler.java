package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.events.player.PlayerDeathEvent;
import me.ftsos.events.game.UhcGamePlayerDeathEvent;
import me.ftsos.events.player.PlayerHitEntityEvent;
import me.ftsos.events.player.PlayerTakeDamageEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameTeam;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListenerHandler implements GameHandler {
    private UhcGame game;

    /**
    * The Main Idea of this class is send greater or more important methods to events, and more handleable methods to keep them inside here or move to utils
    * */

    public GameListenerHandler(UhcGame game) {
        this.game = game;
    }

    /*
    * Not Needed
    * */
    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }

    public void onEvent(Event generalEvent) {
        if(generalEvent instanceof PlayerDeathEvent) {
            PlayerDeathEvent event = (PlayerDeathEvent) generalEvent;
            onPlayerKillEvent(event);
        }

        if(generalEvent instanceof BlockPlaceEvent) {
            onPlayerBlockPlaceEvent((BlockPlaceEvent) generalEvent);
        }

        if(generalEvent instanceof BlockBreakEvent) {
            onPlayerBlockBreakEvent((BlockBreakEvent) generalEvent);
        }

        if(generalEvent instanceof PlayerTakeDamageEvent) {
            onPlayerTakeDamage((PlayerTakeDamageEvent) generalEvent);
        }

        if(generalEvent instanceof FoodLevelChangeEvent) {
            onFoodLevelChange((FoodLevelChangeEvent) generalEvent);
        }

        if(generalEvent instanceof PlayerHitEntityEvent) {
            onPlayerHitEntityEvent((PlayerHitEntityEvent) generalEvent);
        }

        if(generalEvent instanceof PlayerQuitEvent) {
            onPlayerDisconnect((PlayerQuitEvent) generalEvent);
        }
    }

    public void onPlayerKillEvent(PlayerDeathEvent event) {
        if(event.isCancelled()) return;
        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(event.getVictim())) return;

        Player bukkitVictim = event.getVictim();
        Player bukkitKiller = event.getKiller();
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        GamePlayer victim = this.game.getGameTeamHandler().getGamePlayer(bukkitVictim);
        GamePlayer killer = bukkitKiller != null ? this.game.getGameTeamHandler().getGamePlayer(bukkitKiller) : null;
        GameTeam gameTeam = this.game.getGameTeamHandler().getTeam(victim);

        event.setCancelled(true);

        UhcGamePlayerDeathEvent uhcGamePlayerDeathEvent = new UhcGamePlayerDeathEvent(victim, killer, game, gameTeam, damageCause);
        Bukkit.getPluginManager().callEvent(uhcGamePlayerDeathEvent);
    }

    public void onPlayerBlockPlaceEvent(BlockPlaceEvent event) {
        if(event.isCancelled()) return;
        //Checking event for looking if cannot place blocks
        Player player = event.getPlayer();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) return;
        //Check if we can place blocks in the GameState
        if(!(this.game.getGameState() == GameState.WAITING ||
                this.game.getGameState() == GameState.RESTARTING ||
                this.game.getGameState() == GameState.FINISHING ||
                this.game.getGameState() == GameState.STARTING)) return;

        GamePlayer gPlayer = this.game.getGameTeamHandler().getGamePlayer(player);
        gPlayer.sendMessage(Messages.CANT_PLACE_BLOCKS_MESSAGE);
        event.setCancelled(true);
    }

    public void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        //Checking event for looking if cannot place blocks
        Player player = event.getPlayer();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) return;
        //Check if we can place blocks in the GameState
        if(!(this.game.getGameState() == GameState.WAITING ||
                this.game.getGameState() == GameState.RESTARTING ||
                this.game.getGameState() == GameState.FINISHING ||
                this.game.getGameState() == GameState.STARTING)) return;

        GamePlayer gPlayer = this.game.getGameTeamHandler().getGamePlayer(player);
        gPlayer.sendMessage(Messages.CANT_BREAK_BLOCKS_MESSAGE);
        event.setCancelled(true);
    }

    public void onPlayerTakeDamage(PlayerTakeDamageEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) return;

        if(!(this.game.getGameState() == GameState.WAITING ||
                this.game.getGameState() == GameState.RESTARTING ||
                this.game.getGameState() == GameState.FINISHING ||
                this.game.getGameState() == GameState.STARTING)) return;

        event.setCancelled(true);
    }

    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(event.isCancelled()) return;
        if(!(event.getEntity() instanceof Player)) return;
        if(event.getEntity().getType() != EntityType.PLAYER) return;

        Player player = (Player) event.getEntity();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) return;

        if(!(this.game.getGameState() == GameState.WAITING ||
                this.game.getGameState() == GameState.RESTARTING ||
                this.game.getGameState() == GameState.FINISHING ||
                this.game.getGameState() == GameState.STARTING)) return;

        event.setCancelled(true);
    }

    public void onPlayerHitEntityEvent(PlayerHitEntityEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) return;

        if(!(this.game.getGameState() == GameState.WAITING ||
                this.game.getGameState() == GameState.RESTARTING ||
                this.game.getGameState() == GameState.FINISHING ||
                this.game.getGameState() == GameState.STARTING)) return;

        event.setCancelled(true);
    }

    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(player)) {
            if((this.game.getGameState() == GameState.WAITING ||
                    this.game.getGameState() == GameState.RESTARTING ||
                    this.game.getGameState() == GameState.FINISHING ||
                    this.game.getGameState() == GameState.STARTING)) {
                GamePlayer gamePlayer = this.game.getGameTeamHandler().getGamePlayer(player);
                this.game.getGameTeamHandler().onPlayerLeaveInNonPlayingGameState(gamePlayer);
                return;
            }
        }

        if(!this.game.getSpectatorHandler().lobbyContainsBukkitSpectator(player)) {
            return;
        }

    }
}
