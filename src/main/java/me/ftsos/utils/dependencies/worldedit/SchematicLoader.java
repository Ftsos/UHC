package me.ftsos.utils.dependencies.worldedit;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import me.ftsos.UHC;
import me.ftsos.utils.dependencies.DependencyManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.world.registry.WorldData;

public class SchematicLoader {
    private UHC plugin;
    private WorldEditDependency worldEditDependency;
    public SchematicLoader(UHC plugin) {
        this.plugin = plugin;
        this.worldEditDependency = plugin.getManagerHandler().find(DependencyManager.class).find(WorldEditDependency.class);
    }

    /**
     * Loads a schematic from the plugins (Uhc Plugin) folder
     * @param world The world in which the schematic will be used
     * @param schematicName The file name in the schematics folder
     */
    public Schematic load(World world, String schematicName) throws IOException {
        if(!worldEditDependency.isActive()) {
            return null;
        }

        return load(world, new File(plugin.getDataFolder(), schematicName));
    }

    /**
     * Loads a schematic from a file
     * @param world The world in which the schematic will be used
     * @param schematicFile the schematic file to load
     */
    public Schematic load(World world, File schematicFile) throws IOException {
        if(!worldEditDependency.isActive()) {
            return null;
        }

        if(!schematicFile.exists()) throw new IOException("Schematic File " + schematicFile.getName() + " doesn't exists");

        com.sk89q.worldedit.world.World weWorld = worldEditDependency.getWEWorld(world);
        WorldData worldData = weWorld.getWorldData();

        ClipboardFormat format = ClipboardFormat.findByFile(schematicFile);

        Closer closer = Closer.create();

        BufferedInputStream inputStream = closer.register(new BufferedInputStream(closer.register(new FileInputStream(schematicFile))));
        ClipboardReader clipboardReader = null;
        if (format != null) clipboardReader = format.getReader(inputStream);

        Clipboard clipboard = null;
        if (clipboardReader != null) clipboard = clipboardReader.read(worldData);

        ClipboardHolder clipboardHolder = null;
        if (clipboard != null) clipboardHolder = new ClipboardHolder(clipboard, worldData);

        closer.close();

        if(clipboard == null) return null;

        return new Schematic(worldEditDependency.createSession(weWorld), new ClipboardHolder(clipboardHolder.getClipboard(), clipboardHolder.getWorldData()));
    }

    private static Vector toVector(Location location) {
        return new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}