package me.ftsos.commands.commands.uhc.subcommands;

import dev.triumphteam.cmd.core.annotation.Requirement;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.ftsos.commands.utils.ISubCommand;
import me.ftsos.game.GameOptions;
import me.ftsos.game.UhcGamesManagerWrapper;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StartSubCommand implements ISubCommand {
    private UhcGamesManagerWrapper uhcGamesManagerWrapper;
    public StartSubCommand(UhcGamesManagerWrapper uhcGamesManagerWrapper) {
        this.uhcGamesManagerWrapper = uhcGamesManagerWrapper;
    }

    @Requirement("hasPermission:startSubCommand")
    @SubCommand("start")
    public void execute(Player player, GameOptions gameOptions, boolean announce) {
        //TODO: Open gui for scenarios (Scenarios not implemented yet)
        //TODO: Add usage
        player.sendMessage(CC.colorize(Messages.UHC_START_SUB_COMMAND_MESSAGE.replace("%gameName%", gameOptions.getGameName())));
        this.uhcGamesManagerWrapper.createUhcGame(gameOptions);

        if(!announce) return;
        for(Player playerInServer : Bukkit.getOnlinePlayers()) {
            playerInServer.sendMessage(CC.colorize(Messages.UHC_START_SUB_COMMAND_ANNOUNCE_MESSAGE.replace("%gameName%", gameOptions.getGameName())));
        }
    }


}

