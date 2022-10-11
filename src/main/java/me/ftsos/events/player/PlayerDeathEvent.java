package me.ftsos.events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDeathEvent extends Event implements Cancellable {
    private Player victim;
    private Player killer;
    private boolean canceled;
    private EntityDamageEvent.DamageCause cause;
    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerDeathEvent(Player victim, Player killer, EntityDamageEvent.DamageCause damageCause) {
        this.victim = victim;
        this.killer = killer;
        this.cause = damageCause;
        this.canceled = false;
    }

    public PlayerDeathEvent(Player victim, EntityDamageEvent.DamageCause damageCause) {
        this.victim = victim;
        this.killer = null;
        this.cause = damageCause;
        this.canceled = false;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getVictim() {
        return victim;
    }

    public Player getKiller() {
        return killer;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}
