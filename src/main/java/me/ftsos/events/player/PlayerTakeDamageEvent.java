package me.ftsos.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerTakeDamageEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private boolean isCancelled;
    private Player player;
    private EntityDamageEvent.DamageCause damageCause;

    public PlayerTakeDamageEvent(Player player, EntityDamageEvent.DamageCause damageCause) {
        this.player = player;
        this.damageCause = damageCause;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
