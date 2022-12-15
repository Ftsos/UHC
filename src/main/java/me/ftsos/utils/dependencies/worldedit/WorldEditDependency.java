package me.ftsos.utils.dependencies.worldedit;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import me.ftsos.UHC;
import me.ftsos.game.UhcGame;
import me.ftsos.utils.CC;
import me.ftsos.utils.dependencies.Dependency;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.World;

import lombok.AccessLevel;
import lombok.Getter;

public class WorldEditDependency extends Dependency {
    private @Getter WorldEditPlugin worldEditPlugin;
    private @Getter WorldEdit worldEdit;
    private UHC plugin;
    private @Getter(value = AccessLevel.PROTECTED) Map<org.bukkit.World, World> worldCache = new HashMap<>();

    public WorldEditDependency(UHC plugin) {
        this.plugin = plugin;
    }

    public World getWEWorld(org.bukkit.World world) {
        if(worldEditPlugin == null) {
            return null;
        }

        if(worldCache.containsKey(world)) {
            return worldCache.get(world);
        }

        World weWorld = new BukkitWorld(world);
        worldCache.put(world, weWorld);

        return weWorld;
    }

    public EditSession createSession(World world) {
        return worldEdit.getEditSessionFactory().getEditSession(world, Integer.MAX_VALUE);
    }

    @Override
    public boolean isNecessary() {
        return true;
    }

    @Override
    public boolean isActive() {
        return worldEditPlugin != null;
    }

    @Override
    public WorldEditPlugin getDependencyPlugin() {
        return worldEditPlugin;
    }

    @Override
    public void enableDependency() {
        if(worldEditPlugin != null) return;
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");

        if(plugin == null) {
            Bukkit.getLogger().severe(CC.colorize("&cWorld Edit dependency failed to load"));
            disableDependency();
            return;
        }

        worldEditPlugin = (WorldEditPlugin) plugin;
        worldEdit = worldEditPlugin.getWorldEdit();
        Bukkit.getLogger().info(CC.colorize("&aWorld Edit dependency successfully loaded"));
    }

    @Override
    public void disableDependency() {
        worldEditPlugin = null;
        worldEdit = null;
    }
}