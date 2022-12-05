package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.mcyaml.YamlOf;
import me.ftsos.UHC;
import me.ftsos.managers.Manager;

public class ConfigManager extends Manager {
    private final UHC plugin;

    private final IYaml config;
    private final IYaml messages;
    private final IYaml inventories;
    private final IYaml permissions;
    private final IYaml scoreboards;

    public ConfigManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        config = new YamlOf(plugin, "config.yml");
        messages = new YamlOf(plugin, "messages.yml");
        inventories = new YamlOf(plugin, "inventories.yml");
        permissions = new YamlOf(plugin, "permissions.yml");
        scoreboards = new YamlOf(plugin, "scoreboards.yml");
    }

    @Override
    public void enable() {
        config.create();
        messages.create();
        inventories.create();
        permissions.create();
        scoreboards.create();
    }

    @Override
    public void disable() {

    }

    public IYaml getConfig() {
        return config;
    }

    public IYaml getMessages() {
        return messages;
    }

    public IYaml getInventories() {
        return inventories;
    }

    public IYaml getPermissions() {
        return permissions;
    }

    public IYaml getScoreboards() {
        return scoreboards;
    }
}
