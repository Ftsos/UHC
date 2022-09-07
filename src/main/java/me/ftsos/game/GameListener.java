package me.ftsos.game;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.events.player.PlayerDeathEvent;
import me.ftsos.events.game.UhcGamePlayerDeathEvent;
import me.ftsos.utils.config.Messages;
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
            deathMessage = Messages.NATURAL_PLAYER_DEATH_MESSAGE.replace("%player%", event.getVictim().getPlayer().getDisplayName()).replace("%death_cause%", event.getCause().name());
        } else {
            deathMessage = Messages.PLAYER_KILL_MESSAGE.replace("%killer%", event.getKiller().getPlayer().getDisplayName()).replace("%victim%", event.getVictim().getPlayer().getDisplayName());
        }
        event.getGame().broadcastMessage(deathMessage);
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