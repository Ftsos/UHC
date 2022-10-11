package me.ftsos.lobby.handlers;

import me.ftsos.events.player.PlayerChangeWorldEvent;
import me.ftsos.events.player.PlayerFallIntoVoidEvent;
import me.ftsos.events.player.PlayerTakeDamageEvent;
import me.ftsos.items.Items;
import me.ftsos.lobby.Lobby;
import me.ftsos.lobby.player.LobbyPlayer;
import me.ftsos.utils.config.Config;
import me.ftsos.utils.config.Inventories;
import me.ftsos.utils.config.Permissions;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LobbyPlayersHandler implements LobbyHandler{
    private List<LobbyPlayer> lobbyPlayers;
    private List<UUID> allowedBuildingOrBreakingPlayers;
    private Lobby lobby;

    public LobbyPlayersHandler(Lobby lobby) {
        this.lobby = lobby;
        this.lobbyPlayers = new ArrayList<>();
        this.allowedBuildingOrBreakingPlayers = new ArrayList<>();
    }

    @Override
    public void onEvent(Event generalEvent) {
        if(generalEvent instanceof PlayerJoinEvent) {
            PlayerJoinEvent event = (PlayerJoinEvent) generalEvent;
            onPlayerJoinEvent(event);
        }
        if(generalEvent instanceof PlayerQuitEvent) {
            PlayerQuitEvent event = (PlayerQuitEvent) generalEvent;
            onPlayerLeave(event);
        }
        if(generalEvent instanceof PlayerFallIntoVoidEvent) {
            PlayerFallIntoVoidEvent event = (PlayerFallIntoVoidEvent) generalEvent;
            onPlayerFallIntoVoid(event);
        }
        if(generalEvent instanceof PlayerTakeDamageEvent) {
            PlayerTakeDamageEvent event = (PlayerTakeDamageEvent) generalEvent;
            onPlayerTakeDamage(event);
        }
        if(generalEvent instanceof BlockPlaceEvent) {
            BlockPlaceEvent event = (BlockPlaceEvent) generalEvent;
            onBlockBreakOrPlace(event);
        }
        if(generalEvent instanceof BlockBreakEvent) {
            BlockBreakEvent event = (BlockBreakEvent) generalEvent;
            onBlockBreakOrPlace(event);
        }
        if(generalEvent instanceof PlayerChangeWorldEvent) {
            PlayerChangeWorldEvent event = (PlayerChangeWorldEvent) generalEvent;
            onPlayerChangedWorld(event);
        }
    }

    /*
    * Events methods
    * */

    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }

    public void onPlayerChangedWorld(PlayerChangeWorldEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        LobbyPlayer lobbyPlayer = getLobbyPlayer(player);
        //Leaving Lobby
        if(event.getFrom() == lobby.getLobbyWorldHandler().getLobbyWorld()) {
            removePlayer(lobbyPlayer);
            player.getInventory().clear();
        }

        //Getting into the lobby world
        if(event.getTo() == lobby.getLobbyWorldHandler().getLobbyWorld()) {
            addPlayer(player);
        }
    }

    public void onBlockBreakOrPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        if(!playerCanBuildOrBreak(player)) return;
        event.setCancelled(true);
    }

    public void onBlockBreakOrPlace(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        if(!playerCanBuildOrBreak(player)) return;
        event.setCancelled(true);
    }

    public void onPlayerFallIntoVoid(PlayerFallIntoVoidEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        player.teleport(lobby.getLobbyWorldHandler().getSpawnLocation());
    }

    public void onPlayerTakeDamage(PlayerTakeDamageEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        event.setCancelled(true);
    }

    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(!isPlayerInTheLobby(player)) return;
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.sendTitle("", "");
        player.setVelocity(new Vector(0, 0, 0));
        player.setFallDistance(0);
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFoodLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(lobby.getLobbyWorldHandler().getSpawnLocation());
        LobbyPlayer lobbyPlayer = getLobbyPlayer(player);
        removePlayer(lobbyPlayer);
    }

    /*
    * Methods of Players
    * */

    public boolean isPlayerInTheLobby(Player player) {
        if(getLobbyPlayer(player) == null) return false;
        return true;
    }

    public LobbyPlayer getLobbyPlayer(LobbyPlayer player) {
        for(LobbyPlayer lobbyPlayer : this.lobbyPlayers) {
            if(lobbyPlayer.getPlayer().getUniqueId() == player.getPlayer().getUniqueId()) return lobbyPlayer;
        }
        return null;
    }

    public LobbyPlayer getLobbyPlayer(Player player) {
        for(LobbyPlayer lobbyPlayer : this.lobbyPlayers) {
            if(lobbyPlayer.getPlayer().getUniqueId() == player.getUniqueId()) return lobbyPlayer;
        }
        return null;
    }

    public void removePlayer(LobbyPlayer lobbyPlayer) {
        this.lobbyPlayers.remove(lobbyPlayer);
        lobbyPlayer.leaveLobby();
    }

    public void addPlayer(Player player) {
        if(isPlayerInTheLobby(player)) return;
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.sendTitle("", "");
        player.setVelocity(new Vector(0, 0, 0));
        player.setFallDistance(0);
        player.setHealth(player.getMaxHealth());
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(lobby.getLobbyWorldHandler().getSpawnLocation());
        player.getInventory().setItem(Inventories.OPEN_GAMES_ITEM_SLOT_LOBBY, Items.OPEN_GAMES_ITEM_LOBBY);

        LobbyPlayer lobbyPlayer = new LobbyPlayer(player);
        this.lobbyPlayers.add(lobbyPlayer);
    }

    /*
    * Utils
    * */
    public boolean playerCanBuildOrBreak(Player player) {
        if(!player.getPlayer().hasPermission(Permissions.LOBBY_BUILDING_OR_BREAKING_ON_LOBBY_PERMISSION)) return false;
        if(!this.allowedBuildingOrBreakingPlayers.contains(player.getUniqueId())) return false;
        return true;
    }

    public boolean playerCanBuildOrBreak(LobbyPlayer player) {
        if(!player.getPlayer().hasPermission(Permissions.LOBBY_BUILDING_OR_BREAKING_ON_LOBBY_PERMISSION)) return false;
        if(!this.allowedBuildingOrBreakingPlayers.contains(player.getPlayer().getUniqueId())) return false;
        return true;
    }


    public void addPlayerToCanBuildOrBreakList(Player player) {
        this.allowedBuildingOrBreakingPlayers.add(player.getUniqueId());
    }

    public void addPlayerToCanBuildOrBreakList(UUID player) {
        this.allowedBuildingOrBreakingPlayers.add(player);
    }

    public void addPlayerToCanBuildOrBreakList(LobbyPlayer player) {
        this.allowedBuildingOrBreakingPlayers.add(player.getPlayer().getUniqueId());
    }
}

