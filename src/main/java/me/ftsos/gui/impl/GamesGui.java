package me.ftsos.gui.impl;

import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.ftsos.game.UhcGame;
import me.ftsos.gui.Gui;
import me.ftsos.gui.PerPlayerGui;
import me.ftsos.items.Items;
import me.ftsos.utils.ItemBuilder;
import me.ftsos.utils.config.Inventories;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GamesGui extends PerPlayerGui<GamesGui> implements Gui<PaginatedGui>{
    private PaginatedGui gui;
    private static Map<UhcGame, GuiItem> uhcGamesBeingDisplayed = new HashMap<>();

    public GamesGui() {
        this.gui = dev.triumphteam.gui.guis.Gui.paginated()
                .title(Component.text(Inventories.TITLE_GAMES_GUI))
                .rows(Inventories.ROWS_GAMES_GUI)
                .pageSize(Inventories.PAGE_SIZE_GAMES_GUI)
                .create();

        //TODO: Feature: Make the border item customizable
        //Fill the borders with black stained glass pane
        this.gui.getFiller().fillBorder(new ItemBuilder(Material.STAINED_GLASS_PANE).setGlassColor(ItemBuilder.GlassColors.BLACK).setName(" ").toGuiItem(event -> event.setCancelled(true)));

        //Col and row start from 1, 1
        //Add Next Arrow
        this.gui.setItem(Inventories.PREVIOUS_ITEM_ROW_GAMES_GUI, Inventories.PREVIOUS_ITEM_COL_GAMES_GUI, new ItemBuilder(Items.PREVIOUS_ITEM_GAMES_GUI).toGuiItem(event -> {
            event.setCancelled(true);
            this.gui.next();
        }));

        //Add Preview Arrow
        this.gui.setItem(Inventories.NEXT_ITEM_ROW_GAMES_GUI, Inventories.NEXT_ITEM_COL_GAMES_GUI, new ItemBuilder(Items.NEXT_ITEM_GAMES_GUI).toGuiItem(event -> {
            event.setCancelled(true);
            this.gui.previous();
        }));

        registerAllAvailableGames();

        this.gui.update();
        this.updateGuis();
    }

    public void registerAllAvailableGames() {
        for(Map.Entry<UhcGame, GuiItem> entry : uhcGamesBeingDisplayed.entrySet()) {
            this.gui.addItem(entry.getValue());
        }
    }

    @Override
    public PaginatedGui getGui() {
        return this.gui;
    }

    public void onNewGameGettingRegistered(UhcGame uhcGame) {
        if(!uhcGame.getGameOptions().isShouldGameAppearOnGamesGui()) return;
        GuiItem guiItem = new ItemBuilder(uhcGame.getGameOptions().getItemToShowOnGamesGui()).toGuiItem(event -> {
            if(event.getWhoClicked() instanceof Player)
                uhcGame.getGamePlayerWrapperHandler().playerAloneJoin((Player) event.getWhoClicked());
        });
        uhcGamesBeingDisplayed.put(uhcGame, guiItem);
        this.addItemToGuis(guiItem);
        this.updateGuis();
    }



    public void onGameBeingRemoved(UhcGame uhcGame) {
        if(!uhcGame.getGameOptions().isShouldGameAppearOnGamesGui()) return;
        Optional<GuiItem> guiItem = Optional.ofNullable(this.uhcGamesBeingDisplayed.get(uhcGame));
        guiItem.ifPresent(this::removeItemToGuis);
        this.uhcGamesBeingDisplayed.remove(uhcGame);
        this.updateGuis();
    }

}
