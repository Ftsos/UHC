package me.ftsos.events.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHitEntityEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private Entity entity;
    private EntityDamageEvent.DamageCause cause;
    private double damage;
    private double finalDamage;

    public PlayerHitEntityEvent(Player player, Entity entity, EntityDamageEvent.DamageCause cause, double damage, double finalDamage) {
        this.isCancelled = false;
        this.player = player;
        this.entity = entity;
        this.cause = cause;
        this.damage = damage;
        this.finalDamage = finalDamage;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getEntity() {
        return entity;
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

}
