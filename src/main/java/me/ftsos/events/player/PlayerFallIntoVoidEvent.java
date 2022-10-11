package me.ftsos.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerFallIntoVoidEvent extends Event {
    private Player player;
    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerFallIntoVoidEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
