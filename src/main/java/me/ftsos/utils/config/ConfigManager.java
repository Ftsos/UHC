package me.ftsos.utils.config;

import io.github.portlek.mcyaml.IYaml;
import io.github.portlek.mcyaml.YamlOf;
import me.ftsos.UHC;
import me.ftsos.managers.Manager;

public class ConfigManager extends Manager {
    private final UHC plugin;

    private final IYaml config;
    private final IYaml messages;

    public ConfigManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        config = new YamlOf(plugin, "config.yml");
        messages = new YamlOf(plugin, "messages.yml");
    }

    @Override
    public void enable() {
        config.create();
        messages.create();

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
}
