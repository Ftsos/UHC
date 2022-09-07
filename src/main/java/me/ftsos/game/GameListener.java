package me.ftsos.game;

import me.ftsos.events.UhcGameDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameListener implements Listener {
    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;
        event.setCancelled(true);
        //Always set the player to max health
        player.setHealth(player.getMaxHealth());

        UhcGameDeathEvent uhcGameDeathEvent = new UhcGameDeathEvent(player, event.getCause());
        Bukkit.getPluginManager().callEvent(uhcGameDeathEvent);
    }


    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;
        event.setCancelled(true);
        //Always set the player to max health
        player.setHealth(player.getMaxHealth());

        UhcGameDeathEvent uhcGameDeathEvent;

        if(event.getDamager() instanceof Player) {
            Player killer = (Player) event.getDamager();
            uhcGameDeathEvent = new UhcGameDeathEvent(player, killer, event.getCause());
        } else {
             uhcGameDeathEvent = new UhcGameDeathEvent(player, event.getCause());
        }

        Bukkit.getPluginManager().callEvent(uhcGameDeathEvent);
    }
}
