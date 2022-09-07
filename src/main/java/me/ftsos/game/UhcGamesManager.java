package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class UhcGamesManager extends Manager {
    private UHC plugin;
    private List<UhcGame> games;
    public UhcGamesManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.games = new ArrayList<>();
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public void onEvent(Event event) {
        for(UhcGame game : this.games) {
            game.onEvent(event);
        }
    }
}
