package me.ftsos.game.players;

import me.ftsos.utils.Colorizer;
import org.bukkit.entity.Player;

public class GameSpectator {
    private Player player;

    public GameSpectator(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(Colorizer.colorize(message));
    }
}
