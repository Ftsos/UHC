package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.events.player.PlayerDeathEvent;
import me.ftsos.events.game.UhcGamePlayerDeathEvent;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameListenerHandler implements GameHandler {
    private UhcGame game;

    public GameListenerHandler(UhcGame game) {
        this.game = game;
    }

    /*
    * Not Needed
    * */

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }

    public void onEvent(Event event) {
        if(event instanceof PlayerDeathEvent) {
            PlayerDeathEvent playerDeathEvent = (PlayerDeathEvent) event;
            if(!this.game.getGameTeamHandler().lobbyContainsBukkitPlayer(playerDeathEvent.getVictim())) return;

            Player bukkitVictim = playerDeathEvent.getVictim();
            Player bukkitKiller = playerDeathEvent.getKiller();
            EntityDamageEvent.DamageCause damageCause = playerDeathEvent.getCause();

            GamePlayer victim = this.game.getGameTeamHandler().getGamePlayer(playerDeathEvent.getVictim());
            GamePlayer killer = bukkitKiller != null ? this.game.getGameTeamHandler().getGamePlayer(bukkitKiller) : null;
            GameTeam gameTeam = this.game.getGameTeamHandler().getTeam(victim);

            UhcGamePlayerDeathEvent uhcGamePlayerDeathEvent = new UhcGamePlayerDeathEvent(victim, killer, game, gameTeam, damageCause);
            Bukkit.getPluginManager().callEvent(uhcGamePlayerDeathEvent);
        }
    }
}
