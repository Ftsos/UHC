package me.ftsos.gui.impl;

import dev.triumphteam.gui.guis.PaginatedGui;
import me.ftsos.game.UhcGame;
import me.ftsos.gui.Gui;
import me.ftsos.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamesGui implements Gui<PaginatedGui> {
    private PaginatedGui gui;
    private List<UhcGame> uhcGamesBeingDisplayed;

    public GamesGui() {
        //TODO: Add Names From Config
        //TODO: Add Configurable rows and page Size
        this.uhcGamesBeingDisplayed = new ArrayList<>();

        this.gui = dev.triumphteam.gui.guis.Gui.paginated()
                .title(Component.text(""))
                .rows(6)
                .pageSize(45)
                .create();

        //Fill the borders with black
        this.gui.getFiller().fillBorder(new ItemBuilder(Material.STAINED_GLASS_PANE).setGlassColor(ItemBuilder.GlassColors.BLACK).toGuiItem(event -> event.setCancelled(true)));

        //Col and row start from 1, 1
        //TODO: Add Names From Config
        //Add Next Arrow
        this.gui.setItem(6, 1, new ItemBuilder(Material.ARROW).setName("").toGuiItem(event -> {
            event.setCancelled(true);
            this.gui.next();
        }));

        //Add Preview Arrow
        this.gui.setItem(6, 9, new ItemBuilder(Material.ARROW).setName("").toGuiItem(event -> {
            event.setCancelled(true);
            this.gui.previous();
        }));

        for(UhcGame uhcGame : this.uhcGamesBeingDisplayed) {
            if(!uhcGame.getGameOptions().isShouldGameAppearOnGamesGui()) continue;
            this.gui.addItem(new ItemBuilder(uhcGame.getGameOptions().getItemToShowOnGamesGui()).toGuiItem(event -> {
                if(event.getWhoClicked() instanceof Player)
                uhcGame.getGamePlayerWrapperHandler().playerAloneJoin((Player) event.getWhoClicked());
            }));
        }

        //TODO: I think we will need to add a custom method in order to put the items respecting the border, check on server and do a method if needed
        this.gui.update();
    }

    @Override
    public PaginatedGui getGui() {
        return this.gui;
    }

    public void onNewGameGettingRegistered(UhcGame uhcGame) {
        this.uhcGamesBeingDisplayed.add(uhcGame);
        this.gui.addItem(new ItemBuilder(uhcGame.getGameOptions().getItemToShowOnGamesGui()).toGuiItem(event -> {
            if(event.getWhoClicked() instanceof Player)
                uhcGame.getGamePlayerWrapperHandler().playerAloneJoin((Player) event.getWhoClicked());
        }));
        this.gui.update();
    }

    public void onGameBeingRemoved(UhcGame uhcGame) {
        this.uhcGamesBeingDisplayed.remove(uhcGame);
        this.gui.removeItem(uhcGame.getGameOptions().getItemToShowOnGamesGui());
        this.gui.update();
    }
}
