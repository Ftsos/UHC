package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.util.UUID;

public class MapHandler implements GameHandler {
    private World world;
    private Location spawnLocation;
    /**
    * @MapHandler
    * This Class is the one that creates and manages the maps (borders in a future, and 0 0) and all that stuff
    * */
    public MapHandler() {
        //TODO: Make a thread for it
        WorldCreator wc = new WorldCreator("GAME+" + UUID.randomUUID());

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);

        this.world = wc.createWorld();
        this.spawnLocation = new Location(this.world, 0, 0 ,0);
        this.world.setSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ());

    }

    public World getWorld() {
        return world;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }
}
