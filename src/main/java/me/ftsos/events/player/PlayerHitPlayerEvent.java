package me.ftsos.events.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitPlayerEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();
    private Player victim;
    private Player damager;
    private EntityDamageEvent.DamageCause cause;
    private double damage;
    private double finalDamage;

    public PlayerHitPlayerEvent(Player victim, Player damager, EntityDamageEvent.DamageCause cause, double damage, double finalDamage) {
        this.victim = victim;
        this.damager = damager;
        this.cause = cause;
        this.damage = damage;
        this.finalDamage = finalDamage;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getVictim() {
        return victim;
    }

    public Player getDamager() {
        return damager;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }

    public double getDamage() {
        return damage;
    }

    public double getFinalDamage() {
        return finalDamage;
    }
}
