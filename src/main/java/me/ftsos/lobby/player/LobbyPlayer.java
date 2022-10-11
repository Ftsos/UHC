package me.ftsos.lobby.player;

import me.ftsos.utils.CC;
import org.bukkit.entity.Player;

public class LobbyPlayer {
    private Player player;

    public LobbyPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void leaveLobby() {
        player = null;
    }

    public void sendMessage(String message) {
        player.sendMessage(CC.colorize(message));
    }
}
