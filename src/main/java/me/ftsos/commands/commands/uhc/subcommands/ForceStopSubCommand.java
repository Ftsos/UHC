package me.ftsos.commands.commands.uhc.subcommands;


import dev.triumphteam.cmd.core.annotation.Requirement;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.UhcGamesManagerWrapper;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.entity.Player;

public class ForceStopSubCommand extends InfoSubCommand {
    private UhcGamesManagerWrapper uhcGamesManagerWrapper;

    public ForceStopSubCommand(UhcGamesManagerWrapper uhcGamesManagerWrapper) {
        this.uhcGamesManagerWrapper = uhcGamesManagerWrapper;
    }

    @Requirement("hasPermission:forceStopSubCommand")
    @SubCommand("forceStop")
    public void execute(Player player, String gameName) {
        UhcGame game = uhcGamesManagerWrapper.getUhcGame(gameName);
        if(game == null) {
            player.sendMessage(CC.colorize(Messages.UHC_FORCE_STOP_SUB_COMMAND_GAME_DOESNT_EXIST_MESSAGE.replace("%gameName%", gameName)));
            return;
        }

        game.updateGameState(GameState.FINISHING);
        player.sendMessage(CC.colorize(Messages.UHC_FORCE_STOP_SUB_COMMAND_GAME_STOPPED_MESSAGE.replace("%gameName%", gameName)));
    }
}
