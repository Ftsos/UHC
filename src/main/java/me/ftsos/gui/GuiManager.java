package me.ftsos.gui;

import me.ftsos.UHC;
import me.ftsos.gui.impl.GamesGui;
import me.ftsos.managers.Manager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuiManager extends Manager {
    private UHC plugin;
    private Map<Class<? extends Gui>, Gui> guis;

    public GuiManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.guis = new HashMap<>();
    }

    @Override
    public void enable() {
        registerGuis();
    }

    @Override
    public void disable() {

    }

    public <T extends Gui> T get(Class<T> clazz) {
        return clazz.cast(this.guis.get(clazz));
    }


    public void registerGui(Gui gui) {
        this.guis.put(gui.getClass(), gui);
    }

    public void openGui(Class<? extends Gui> guiClazz, Player player) {
        this.get(guiClazz).openGuiToPlayer(player);
    }

    public void registerGuis() {
        this.registerGui(new GamesGui());
    }

    public List<Gui> getGuis() {
        return this.guis.values().stream().collect(Collectors.toList());
    }
}
