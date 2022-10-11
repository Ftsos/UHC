package me.ftsos.commands.commands.uhc;

import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.Requirement;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.entity.Player;

@Command(value = "uhc")
public class UhcCommand extends BaseCommand {

    @Requirement("hasPermission:baseCommand")
    @Default
    public void executor(Player player) {
        CC.colorizeMessageListAndSendIt(player, Messages.UHC_INFO_SUB_COMMAND_MESSAGE);
    }

}
