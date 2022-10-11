package me.ftsos.listeners;

import me.ftsos.events.player.*;
import me.ftsos.utils.tasks.Task;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MainListener implements Listener {
    /*
     *
     * Firing Players Events
     *
     * */

    public MainListener() {
        onPlayerFallIntoVoid();
    }

    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;

        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, event.getCause());
        Bukkit.getPluginManager().callEvent(playerDeathEvent);

        if(playerDeathEvent.isCancelled()) {
            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());
        }
    }


    @EventHandler
    public void onKill(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if(!(player.getHealth() - event.getFinalDamage() <= 0)) return;

        PlayerDeathEvent playerDeathEvent;

        if(event.getDamager() instanceof Player) {
            Player killer = (Player) event.getDamager();
            playerDeathEvent = new PlayerDeathEvent(player, killer, event.getCause());
        } else {
            playerDeathEvent = new PlayerDeathEvent(player, event.getCause());
        }

        Bukkit.getPluginManager().callEvent(playerDeathEvent);

        if(playerDeathEvent.isCancelled()) {
            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());
        }
    }

    @EventHandler
    public void onPlayerGetsHitByEntity(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(event.getEntity().getType() != EntityType.PLAYER) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        PlayerGetsHitByEntityEvent playerGetsHitByEntityEvent = new PlayerGetsHitByEntityEvent(player, event.getDamager(), event.getCause(), event.getDamage(), event.getFinalDamage());
        Bukkit.getPluginManager().callEvent(playerGetsHitByEntityEvent);

        if(playerGetsHitByEntityEvent.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTakeDamage(EntityDamageEvent event) {
        if(event.isCancelled()) return;
        if(event.getEntity().getType() != EntityType.PLAYER) return;
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();

        PlayerTakeDamageEvent playerTakeDamageEvent = new PlayerTakeDamageEvent(player, event.getCause());
        Bukkit.getPluginManager().callEvent(playerTakeDamageEvent);

        if(playerTakeDamageEvent.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHitPlayerEvent(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;
        if(event.getEntity().getType() != EntityType.PLAYER) return;
        if(event.getDamager().getType() != EntityType.PLAYER) return;
        if(!(event.getEntity() instanceof Player)) return;
        if(!(event.getDamager() instanceof Player)) return;
        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        PlayerHitPlayerEvent playerHitPlayerEvent = new PlayerHitPlayerEvent(victim, damager, event.getCause(), event.getDamage(), event.getFinalDamage());
        Bukkit.getPluginManager().callEvent(playerHitPlayerEvent);

        if(playerHitPlayerEvent.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerTeleportEvent event) {
        if(event.getFrom().getWorld() == event.getTo().getWorld()) return;
        //Changing World

        PlayerChangeWorldEvent playerChangeWorldEvent = new PlayerChangeWorldEvent(event.getPlayer(), event.getFrom().getWorld(), event.getTo().getWorld(), event.getFrom(), event.getTo());
        Bukkit.getPluginManager().callEvent(playerChangeWorldEvent);
    }

    public void onPlayerFallIntoVoid(){
        Task voidTask = new Task() {
            @Override
            public void runCustomTask() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Location location = player.getLocation();
                    if(!(location.getY() < 0)) continue;

                    PlayerFallIntoVoidEvent event = new PlayerFallIntoVoidEvent(player);
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        };

        voidTask.runTaskTimer(0, 20);
    }

}
