package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GameSpectator;
import me.ftsos.items.Items;
import me.ftsos.lobby.Lobby;
import me.ftsos.utils.config.Inventories;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SpectatorHandler implements GameHandler{
    private List<GameSpectator> spectators;
    private UhcGame uhcGame;
    private Lobby lobby;
    public SpectatorHandler(UhcGame game) {
        this.spectators = new ArrayList<>();
        this.uhcGame = game;
        this.lobby = this.uhcGame.getLobby();
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;
        if(event.getNewGameState() != GameState.RESTARTING) return;
        for(GameSpectator spectator : this.spectators) {
            removeSpectator(spectator);
        }
        this.spectators.clear();
    }

    public void addSpectator(GameSpectator spectator) {
        spectators.add(spectator);
        spectator.getPlayer().setHealth(spectator.getPlayer().getMaxHealth());
        spectator.getPlayer().getInventory().clear();
        spectator.getPlayer().getInventory().setArmorContents(null);
        spectator.getPlayer().setVelocity(new Vector(0, 0, 0));
        spectator.getPlayer().setFireTicks(0);
        spectator.getPlayer().setFoodLevel(20);
        spectator.getPlayer().setFallDistance(0);
        spectator.getPlayer().setGameMode(GameMode.SPECTATOR);
        spectator.getPlayer().sendTitle("", "");
        spectator.getPlayer().teleport(uhcGame.getMapHandler().getSpawnLocation());
        givePlayerSpectatorInventory(spectator.getPlayer());
    }

    public List<GameSpectator> getSpectators() {
        return spectators;
    }

    public boolean lobbyContainsBukkitSpectator(Player player) {
        if(getSpectator(player) != null) return true;
        return false;
    }

    public boolean lobbyContainsSpectator(GameSpectator gameSpectator) {
        if(this.spectators.contains(gameSpectator)) return true;
        return false;
    }

    public GameSpectator getSpectator(Player player) {
        for(GameSpectator gSpectator : this.spectators) {
            if(gSpectator.getPlayer().getUniqueId() == player.getUniqueId()) return gSpectator;
        }
        return null;
    }

    public void removeSpectator(GameSpectator spectator) {
        this.spectators.remove(spectator);
        spectator.getPlayer().getInventory().clear();
        spectator.getPlayer().setHealth(spectator.getPlayer().getMaxHealth());
        spectator.getPlayer().getInventory().clear();
        spectator.getPlayer().getInventory().setArmorContents(null);
        spectator.getPlayer().setVelocity(new Vector(0, 0, 0));
        spectator.getPlayer().setFireTicks(0);
        spectator.getPlayer().setFoodLevel(20);
        spectator.getPlayer().setFallDistance(0);
        spectator.getPlayer().setGameMode(GameMode.SPECTATOR);
        spectator.getPlayer().sendTitle("", "");
        spectator.leaveSpectatorMode();
        lobby.getLobbyPlayersHandler().addPlayer(spectator.getPlayer());
    }
    /*
    * Utils
    * */

    public void givePlayerSpectatorInventory(Player player) {
        player.getInventory().clear();
        //Set Last Item to be the bed leave
        player.getInventory().setItem(Inventories.LEAVE_ITEM_SLOT_SPECTATOR, Items.LEAVE_ITEM_SPECTATOR_MENU);
        player.getInventory().setItem(Inventories.TELEPORT_ITEM_SLOT_SPECTATOR, Items.TELEPORT_ITEM_SPECTATOR_MENU);
        player.getInventory().setItem(Inventories.PLAY_AGAIN_ITEM_SLOT_SPECTATOR, Items.PLAY_AGAIN_ITEM_SPECTATOR_MENU);
    }
}
