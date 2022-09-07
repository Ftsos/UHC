package me.ftsos.game.players;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GameTeam {
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
            gPlayer.getPlayer().teleport(location);
        }
    }

    public void onPlayerKilled(GamePlayer gamePlayer) {
        this.players.remove(gamePlayer);
    }

    public boolean containsPlayer(Player player) {
        if(getGamePlayer(player) == null) return false;
        return true;
    }

    public boolean containsPlayer(UUID player) {
        if(getGamePlayer(player) == null) return false;
        return true;
    }

    public boolean containsGamePlayer(GamePlayer player) {
        for(GamePlayer gPlayer : this.players) {
            if(player.getPlayer().getUniqueId() == gPlayer.getPlayer().getUniqueId()) return true;
        }
        return false;
    }

    public GamePlayer getGamePlayer(Player player) {
        for(GamePlayer gPlayer : this.players) {
            if(player.getUniqueId() == gPlayer.getPlayer().getUniqueId()) return gPlayer;
        }
        return null;
    }

    public GamePlayer getGamePlayer(UUID player) {
        for(GamePlayer gPlayer : this.players) {
            if(player == gPlayer.getPlayer().getUniqueId()) return gPlayer;
        }
        return null;
    }
}
