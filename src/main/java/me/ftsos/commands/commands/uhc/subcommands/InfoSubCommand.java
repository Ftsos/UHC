package me.ftsos.commands.commands.uhc.subcommands;

import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.ftsos.commands.utils.ISubCommand;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class InfoSubCommand implements ISubCommand {

    @SubCommand(value = "info", alias = {"help"})
    public void infoSubCommand(CommandSender sender) {
        Bukkit.broadcastMessage(String.valueOf(Messages.UHC_INFO_SUB_COMMAND_MESSAGE));
        CC.colorizeMessageList(Messages.UHC_INFO_SUB_COMMAND_MESSAGE).forEach(
                message -> {
                    sender.sendMessage(message);
                }
        );
    }
}
