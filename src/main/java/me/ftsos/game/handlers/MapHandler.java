package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;

public class MapHandler implements GameHandler {
    private World world;
    private Location spawnLocation;
    private UhcGame uhcGame;
    /**
    * @MapHandler
    * This Class is the one that creates and manages the maps (borders in a future, and 0 0) and all that stuff
    * */
    public MapHandler(UhcGame uhcGame) {
        this.uhcGame = uhcGame;
        //TODO: Make a thread for it
        WorldCreator wc = new WorldCreator("GAME+" + UUID.randomUUID());

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);

        this.world = wc.createWorld();

        //TODO: Customizable spawn location
        //TODO: Add default rules for uhc map
        this.spawnLocation = new Location(this.world, 0, this.world.getHighestBlockYAt(0, 0) ,0);
        this.world.setSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ());

    }

    public World getWorld() {
        return world;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;
        if(event.getNewGameState() != GameState.RESTARTING) return;
        deleteWorld(this.world);
        this.world = null;
    }

    /**
     * Basic World Utils
     */
    private void deleteWorld(World world) {
        world.getPlayers().forEach(player -> player.teleport(this.uhcGame.getLobby().getLobbyWorldHandler().getSpawnLocation()));
        Bukkit.unloadWorld(world, false);
        if(!world.getWorldFolder().exists()) return;
        File[] files = world.getWorldFolder().listFiles();
        if(files != null) {
            Arrays.stream(files).forEach(File::delete);
        }
        world.getWorldFolder().delete();
    }
}
