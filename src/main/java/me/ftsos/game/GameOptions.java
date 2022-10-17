package me.ftsos.game;

import me.ftsos.utils.CC;
import me.ftsos.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GameOptions {
    private int minTeams;
    private int maxTeams;
    private int maxTeamSize;
    private String gameName;
    private String displayName;
    private ItemStack itemToShowOnGamesGui;
    private boolean shouldGameAppearOnGamesGui;

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName, ItemStack itemToShowOnGamesGui, boolean shouldGameAppearOnGamesGui) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui = itemToShowOnGamesGui;
        this.shouldGameAppearOnGamesGui = shouldGameAppearOnGamesGui;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName, String itemToShowOnGamesGuiName, boolean shouldGameAppearOnGamesGui) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui = new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(itemToShowOnGamesGuiName)).toItemStack();
        this.shouldGameAppearOnGamesGui = shouldGameAppearOnGamesGui;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName, String itemToShowOnGamesGuiName) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui = new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(itemToShowOnGamesGuiName)).toItemStack();
        this.shouldGameAppearOnGamesGui = true;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName, boolean shouldGameAppearOnGamesGui) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui =  new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(this.displayName)).toItemStack();
        this.shouldGameAppearOnGamesGui = shouldGameAppearOnGamesGui;
    }


    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui =  new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(this.displayName)).toItemStack();
        this.shouldGameAppearOnGamesGui = true;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, String displayName, ItemStack itemToShowOnGamesGui) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = displayName;
        this.itemToShowOnGamesGui = itemToShowOnGamesGui;
        this.shouldGameAppearOnGamesGui = true;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName, boolean shouldGameAppearOnGamesGui) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = "&6" + gameName;
        this.itemToShowOnGamesGui = new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(this.displayName)).toItemStack();
        this.shouldGameAppearOnGamesGui = shouldGameAppearOnGamesGui;
    }

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
        this.displayName = "&6" + gameName;
        this.itemToShowOnGamesGui = new ItemBuilder(Material.GOLDEN_APPLE).setName(CC.colorize(this.displayName)).toItemStack();
        this.shouldGameAppearOnGamesGui = true;
    }

    public int getMinTeams() {
        return minTeams;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemStack getItemToShowOnGamesGui() {
        return itemToShowOnGamesGui;
    }

    public boolean isShouldGameAppearOnGamesGui() {
        return shouldGameAppearOnGamesGui;
    }
}
