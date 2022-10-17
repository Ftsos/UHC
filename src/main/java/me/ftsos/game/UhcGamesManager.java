package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;

import java.util.ArrayList;
import java.util.List;

public class UhcGamesManager extends Manager {
    private UHC plugin;
    private List<UhcGame> games;
    private UhcGamesManagerWrapper uhcGamesManagerWrapper;
    //TODO: add support for removing from games list when game has started restarting
    /**
     * The manager of the uhc games, used to create or remove them
     * The methods on this class should only be used on @UhcGamesManagerWrapper
     * */
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


    public void addUhcGame(UhcGame uhcGame) {
        this.games.add(uhcGame);
    }

    public void removeUhcGame(UhcGame uhcGame) {
        this.games.remove(uhcGame);
    }

    public List<UhcGame> getGames() {
        return games;
    }

    /**
     * Only method that should be used outside of @UhcGamesManagerWrapper
     */
    public UhcGamesManagerWrapper getUhcGamesManagerWrapper() {
        return uhcGamesManagerWrapper;
    }

}
