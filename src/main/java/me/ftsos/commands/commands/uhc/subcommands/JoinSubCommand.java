package me.ftsos.commands.commands.uhc.subcommands;

import dev.triumphteam.cmd.core.annotation.Requirement;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.ftsos.commands.utils.ISubCommand;
import me.ftsos.game.UhcGame;
import me.ftsos.game.UhcGamesManagerWrapper;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.entity.Player;

public class JoinSubCommand implements ISubCommand {
    private UhcGamesManagerWrapper uhcGamesManagerWrapper;

    public JoinSubCommand(UhcGamesManagerWrapper uhcGamesManagerWrapper) {
        this.uhcGamesManagerWrapper = uhcGamesManagerWrapper;
    }

    @Requirement("hasPermission:joinSubCommand")
    @SubCommand("join")
    public void execute(Player player, String gameName) {
        UhcGame game = uhcGamesManagerWrapper.getUhcGame(gameName);
        if(game == null) {
            player.sendMessage(CC.colorize(Messages.UHC_JOIN_SUB_COMMAND_GAME_DOESNT_EXIST_MESSAGE.replace("%gameName%", gameName)));
            return;
        }

        //TODO: Add party checks and check if the player is in a party
        game.getGamePlayerWrapperHandler().playerAloneJoin(player);
    }
}
