package me.ftsos.events.game;

import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

public class UhcGamePlayerDeathEvent extends Event {
    private GamePlayer victim;
    private GamePlayer killer;
    private UhcGame game;
    private GameTeam team;
    private EntityDamageEvent.DamageCause cause;
    private static final HandlerList HANDLERS = new HandlerList();

    public UhcGamePlayerDeathEvent(GamePlayer victim, GamePlayer killer, UhcGame game, GameTeam team, EntityDamageEvent.DamageCause cause) {
        this.victim = victim;
        this.killer = killer;
        this.game = game;
        this.team = team;
        this.cause = cause;
    }

    public UhcGamePlayerDeathEvent(GamePlayer victim, UhcGame game, GameTeam team, EntityDamageEvent.DamageCause cause) {
        this.victim = victim;
        this.game = game;
        this.team = team;
        this.cause = cause;
        this.killer = null;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public GamePlayer getVictim() {
        return victim;
    }

    public GamePlayer getKiller() {
        return killer;
    }

    public UhcGame getGame() {
        return game;
    }

    public GameTeam getTeam() {
        return team;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }
}