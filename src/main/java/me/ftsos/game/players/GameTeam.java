package me.ftsos.game.players;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GameTeam {
    //TODO: Add game team name

    private List<GamePlayer> players;

    public GameTeam(List<GamePlayer> players) {
        this.players = players;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public int getSize() {
        return players.size();
    }

    public void teleport(Location location) {
        for(GamePlayer gPlayer : this.players) {
            gPlayer.getPlayer().ifPresent(player -> {
                player.teleport(location);
            });
        }
    }

    public void onPlayerKilled(GamePlayer gamePlayer) {
        //TODO: Finish the checks to see if the team has lost, should be done with cooperation of @GameTeamHandler
        this.players.remove(gamePlayer);
        gamePlayer.onPlayerKilled();
    }

    public boolean containsPlayer(Player player) {
        if(getGamePlayer(player) == null) return false;
        return true;
    }

    public boolean containsPlayer(UUID player) {
        if(getGamePlayer(player) == null) return false;
        return true;
    }

    public boolean containsGamePlayer(GamePlayer gamePlayer) {
        for(GamePlayer gPlayer : this.players) {
                if(gamePlayer.getPlayerUUID() == gPlayer.getPlayerUUID()) return true;
        }
        return false;
    }

    public GamePlayer getGamePlayer(Player player) {
        for(GamePlayer gPlayer : this.players) {
                if(player.getUniqueId() == gPlayer.getPlayerUUID()) return gPlayer;
        }
        return null;
    }

    public GamePlayer getGamePlayer(GamePlayer player) {
        for(GamePlayer gPlayer : this.players) {
            if(player.getPlayerUUID() == gPlayer.getPlayerUUID()) return gPlayer;
        }
        return null;
    }

    public GamePlayer getGamePlayer(UUID player) {
        for(GamePlayer gPlayer : this.players) {
            if(player == gPlayer.getPlayerUUID()) return gPlayer;
        }
        return null;
    }


}
