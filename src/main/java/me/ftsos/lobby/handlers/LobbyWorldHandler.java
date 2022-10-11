package me.ftsos.lobby.handlers;

import me.ftsos.utils.config.Config;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.Event;

public class LobbyWorldHandler implements LobbyHandler{
    private World lobbyWorld;
    private Location spawnLocation;

    public LobbyWorldHandler() {
        WorldCreator wc = new WorldCreator(Config.LOBBY_WORLD_NAME);

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("2;0;1");
        wc.generateStructures(false);

        this.lobbyWorld = wc.createWorld();
        this.spawnLocation = new Location(this.lobbyWorld, Config.LOBBY_SPAWN_X_COORD, Config.LOBBY_SPAWN_Y_COORD, Config.LOBBY_SPAWN_Z_COORD, Config.LOBBY_SPAWN_YAW_COORD, Config.LOBBY_SPAWN_PITCH_COORD);
        this.lobbyWorld.setSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ());
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public void onEvent(Event event) {

    }
}
