package me.ftsos.game.handlers;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import me.ftsos.UHC;
import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Config;
import me.ftsos.utils.dependencies.worldedit.Schematic;
import me.ftsos.utils.dependencies.worldedit.SchematicLoader;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;

import java.io.IOException;
import java.util.UUID;

public class MapHandler implements GameHandler {
    private World world;
    private Location spawnLocation;
    private UhcGame uhcGame;
    private Schematic lobbySchematic;
    /**
    * @MapHandler
    * This Class is the one that creates and manages the maps (borders in a future, and 0 0) and all that stuff
    * */
    public MapHandler(UHC plugin, UhcGame uhcGame) {
        this.uhcGame = uhcGame;
        //TODO: Make a thread for it
        WorldCreator wc = new WorldCreator("GAME+" + UUID.randomUUID());

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);

        this.world = wc.createWorld();

        //TODO: Customizable spawn location
        //TODO: Add default rules for uhc map
        this.lobbySchematic = loadLobbySchematic(plugin);
        int heightToPasteSchematic = Config.LOBBY_IN_GAME_HEIGHT_TO_PASTE_SCHEMATIC < this.world.getHighestBlockYAt(0, 0) ? lobbySchematic.getClipboardHolder().getClipboard().getDimensions().getBlockY() - 255 : Config.LOBBY_IN_GAME_HEIGHT_TO_PASTE_SCHEMATIC;
        this.spawnLocation = new Location(this.world, 0, heightToPasteSchematic,0);
        this.world.setSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ());
        pasteLobbySchematic(lobbySchematic, this.spawnLocation);
    }

    public Schematic loadLobbySchematic(UHC plugin) {
        Schematic toReturn;
        try {
            toReturn = new SchematicLoader(plugin).load(this.world, Config.LOBBY_IN_GAME_SCHEMATIC);
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.getLogger().severe(CC.colorize("&cCouldn't load the schematic '" + Config.LOBBY_IN_GAME_SCHEMATIC + "' due to the previously shown exception. Game trying to load the schematic: '" + this.uhcGame.getGameOptions().getGameName() + "'"));
            toReturn = null;
        }
        return toReturn;
    }

    public void pasteLobbySchematic(Schematic schematic, Location location) {
        if(schematic == null) {
            return;
        }
        try {
            schematic.paste(location);
        } catch (WorldEditException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe(CC.colorize("&cCouldn't paste the schematic '" + Config.LOBBY_IN_GAME_SCHEMATIC + "' due to the previously shown exception. Game trying to paste the schematic: '" + this.uhcGame.getGameOptions().getGameName() + "'"));

        }
    }

    public World getWorld() {
        return world;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;
        if(event.getNewGameState() == GameState.PLAYING) {
            lobbySchematic.getEditSession().undo(lobbySchematic.getEditSession());
            return;
        }
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
        try {
            FileUtils.deleteDirectory(world.getWorldFolder());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
