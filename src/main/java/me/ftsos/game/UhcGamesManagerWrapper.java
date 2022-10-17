package me.ftsos.game;

import me.ftsos.UHC;
import me.ftsos.gui.GuiManager;
import me.ftsos.gui.impl.GamesGui;
import org.bukkit.event.Event;

public class UhcGamesManagerWrapper {
    private UhcGamesManager uhcGamesManager;
    private UHC plugin;

    /**
    * @UhcGamesManagerWrapper
    * This class is a wrapper with methods to limit the access to the actual @UhcGamesManagerClass, and also, adds methods to handle common things like game creation or game search
    * */

    public UhcGamesManagerWrapper(UHC plugin, UhcGamesManager uhcGamesManager) {
        this.plugin = plugin;
        this.uhcGamesManager = uhcGamesManager;
    }

    public UhcGame createUhcGame(GameOptions gameOptions) {
        UhcGame uhcGame = new UhcGame(gameOptions, this.plugin);
        this.uhcGamesManager.addUhcGame(uhcGame);

        //Registering the game to the GamesGui
        this.plugin.getManagerHandler().find(GuiManager.class).getGuis().forEach(gui -> {
            if(gui instanceof GamesGui) ((GamesGui) gui).onNewGameGettingRegistered(uhcGame);
        });
        return uhcGame;
    }

    public UhcGame getUhcGame(String gameName) {
        for(UhcGame uhcGame : this.uhcGamesManager.getGames()) {
            if(uhcGame.getGameOptions().getGameName().equals(gameName)) return uhcGame;
        }
        return null;
    }

    public void onEvent(Event event) {
        for(UhcGame game : this.uhcGamesManager.getGames()) {
            game.onEvent(event);
        }
    }

    public void removeUhcGame(UhcGame game) {
        this.uhcGamesManager.removeUhcGame(game);
        this.plugin.getManagerHandler().find(GuiManager.class).getGuis().forEach(gui -> {
            if(gui instanceof GamesGui) ((GamesGui) gui).onGameBeingRemoved(game);
        });
    }
}
