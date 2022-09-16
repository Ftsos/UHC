package me.ftsos.game;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.events.player.PlayerDeathEvent;
import me.ftsos.events.game.UhcGamePlayerDeathEvent;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
        uhcGamesManager.onEvent(event);
    }

    /*
    * Players Game Listeners
    * */

    @EventHandler
    public void onGamePlayerDeath(UhcGamePlayerDeathEvent event) {
        String deathMessage = "";
        if(event.getKiller() == null) {
            deathMessage = Messages.NATURAL_PLAYER_DEATH_MESSAGE.replace("%player%", Bukkit.getOfflinePlayer(event.getVictim().getPlayerUUID()).getName()).replace("%death_cause%", event.getCause().name());
        } else {
            deathMessage = Messages.PLAYER_KILL_MESSAGE.replace("%killer%", Bukkit.getOfflinePlayer(event.getKiller().getPlayerUUID()).getName()).replace("%victim%", Bukkit.getOfflinePlayer(event.getVictim().getPlayerUUID()).getName());
        }
        event.getGame().broadcastMessage(deathMessage);

        //TODO: Change to teleport to lobby manager

    }

    /*
    * GameStates Updates Listener
    * */
    @EventHandler
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;

        UhcGame game = event.getGame();

        game.getMapHandler().onGameStateUpdate(event);
    }
}