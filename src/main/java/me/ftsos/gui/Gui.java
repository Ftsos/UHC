package me.ftsos.gui;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface Gui<T extends BaseGui> {

    T getGui();

    default GuiType getGuiInventoryType() {
        return this.getGui().guiType();
    }

    default List<GuiItem> getGuiItems() {
        return this.getGui().getGuiItems().values().stream().collect(Collectors.toList());
    }

    default GuiInstanceType getGuiInstanceType() {
        return GuiInstanceType.getInstanceType(this.getGui());
    }

    default void openGuiToPlayer(Player player) {
        this.getGui().open(player);
    }
}
