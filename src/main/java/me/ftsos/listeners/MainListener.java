package me.ftsos.listeners;

import me.ftsos.events.player.PlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class MainListener implements Listener {
    /*
     *
     * Firing Players Events
     *
     * */

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;
        event.setCancelled(true);
        //Always set the player to max health
        player.setHealth(player.getMaxHealth());

        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, event.getCause());
        Bukkit.getPluginManager().callEvent(playerDeathEvent);
    }


    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;
        event.setCancelled(true);
        //Always set the player to max health
        player.setHealth(player.getMaxHealth());

        PlayerDeathEvent playerDeathEvent;

        if(event.getDamager() instanceof Player) {
            Player killer = (Player) event.getDamager();
            playerDeathEvent = new PlayerDeathEvent(player, killer, event.getCause());
        } else {
            playerDeathEvent = new PlayerDeathEvent(player, event.getCause());
        }

        Bukkit.getPluginManager().callEvent(playerDeathEvent);
    }
}
