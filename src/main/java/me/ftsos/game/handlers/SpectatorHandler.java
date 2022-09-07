package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GameSpectator;
import org.bukkit.GameMode;

import java.util.ArrayList;
import java.util.List;

public class SpectatorHandler implements GameHandler{
    private List<GameSpectator> spectators;
    private UhcGame uhcGame;
    public SpectatorHandler() {
        this.spectators = new ArrayList<>();
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }

    public void addSpectator(GameSpectator spectator) {
        spectators.add(spectator);
        spectator.getPlayer().setHealth(spectator.getPlayer().getMaxHealth());
        spectator.getPlayer().setGameMode(GameMode.SPECTATOR);
        spectator.getPlayer().teleport(uhcGame.getMapHandler().getSpawnLocation());
    }


}
