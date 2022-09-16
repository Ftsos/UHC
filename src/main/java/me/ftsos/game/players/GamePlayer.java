package me.ftsos.game.players;

import me.ftsos.utils.Colorizer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class GamePlayer {
    //Use UUID for offline support
    private UUID playerUUID;

    public GamePlayer(Player player) {
        this.playerUUID = player.getUniqueId();
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(playerUUID));
    }

    public void sendMessage(String message) {
        getPlayer().ifPresent(player ->  {
            player.sendMessage(Colorizer.colorize(message));
        });
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }
}
