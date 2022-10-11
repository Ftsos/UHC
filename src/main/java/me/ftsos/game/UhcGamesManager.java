package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class UhcGamesManager extends Manager {
    private UHC plugin;
    private List<UhcGame> games;
    private UhcGamesManagerWrapper uhcGamesManagerWrapper;
    //TODO: add support for removing from games list when game has started restarting
    public UhcGamesManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
        this.games = new ArrayList<>();
        this.uhcGamesManagerWrapper = new UhcGamesManagerWrapper(this.plugin, this);
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

    /**
    * This method should only be used on @UhcGamesManagerWrapper
    * */
    public void addUhcGame(UhcGame uhcGame) {
        this.games.add(uhcGame);
    }

    public List<UhcGame> getGames() {
        return games;
    }

    public UhcGamesManagerWrapper getUhcGamesManagerWrapper() {
        return uhcGamesManagerWrapper;
    }
}
