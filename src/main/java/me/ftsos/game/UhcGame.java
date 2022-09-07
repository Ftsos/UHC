package me.ftsos.game;

import me.ftsos.events.UhcGameDeathEvent;
import me.ftsos.game.handlers.GameTeamHandler;
import me.ftsos.game.handlers.MapHandler;
import me.ftsos.utils.Colorizer;
import me.ftsos.utils.config.Messages;
import org.bukkit.event.Event;

public class UhcGame {
    private GameState gameState;
    private GameTeamHandler gameTeamHandler;
    private GameOptions gameOptions;
    private MapHandler mapHandler;

    public UhcGame(GameOptions options) {
        this.gameOptions = options;
        this.mapHandler = new MapHandler();
        this.gameState = GameState.WAITING;
        this.gameTeamHandler = new GameTeamHandler(this);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void broadcastMessage(String message) {
        this.gameTeamHandler.broadcastMessage(Colorizer.colorize(message));
    }

    public GameTeamHandler getGameTeamHandler() {
        return gameTeamHandler;
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public void onEvent(Event event) {
        if(event instanceof UhcGameDeathEvent) {
            UhcGameDeathEvent uhcGameDeathEvent = (UhcGameDeathEvent) event;
            if(!this.getGameTeamHandler().lobbyContainsBukkitPlayer(uhcGameDeathEvent.getVictim())) return;
            String deathMessage = "";
            if(uhcGameDeathEvent.getKiller() == null) {
                deathMessage = Messages.NATURAL_PLAYER_DEATH_MESSAGE.replace("%player%", uhcGameDeathEvent.getVictim().getDisplayName()).replace("%death_cause%", uhcGameDeathEvent.getCause().name());
            } else {
                deathMessage = Messages.PLAYER_KILL_MESSAGE.replace("%killer%", uhcGameDeathEvent.getKiller().getDisplayName()).replace("%victim%", uhcGameDeathEvent.getVictim().getDisplayName());
            }
            broadcastMessage(deathMessage);
        }
    }
}

