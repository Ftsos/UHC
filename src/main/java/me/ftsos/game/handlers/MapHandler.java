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

    public MapHandler() {
        WorldCreator wc = new WorldCreator(UUID.randomUUID() + "");

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);

        this.world = wc.createWorld();

        this.spawnLocation = new Location(this.world, 0, 0 ,0);

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
