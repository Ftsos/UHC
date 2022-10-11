package me.ftsos.commands.utils;

import org.bukkit.command.CommandSender;

public interface Argument {
    <T extends CommandSender> Object validate(T sender, String argument);
}
