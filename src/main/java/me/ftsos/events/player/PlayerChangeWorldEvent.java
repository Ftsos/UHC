package me.ftsos.events.player;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeWorldEvent extends Event {
    private Player player;
    private World from;
    private World to;
    private Location fromLocation;
    private Location toLocation;
    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerChangeWorldEvent(Player player, World from, World to, Location fromLocation, Location toLocation) {
        this.player = player;
        this.from = from;
        this.to = to;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public World getFrom() {
        return from;
    }

    public World getTo() {
        return to;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
