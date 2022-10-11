package me.ftsos.commands.utils;

import org.bukkit.command.CommandSender;

public interface Requirement {
    String getName();
    <T extends CommandSender> boolean validate(T sender);
}
