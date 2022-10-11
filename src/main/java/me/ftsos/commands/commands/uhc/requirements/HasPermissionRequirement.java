package me.ftsos.commands.commands.uhc.requirements;

import me.ftsos.commands.utils.Requirement;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.command.CommandSender;

public class HasPermissionRequirement implements Requirement {
    private String key;
    private String permission;

    public HasPermissionRequirement(String key, String permission) {
        this.permission = permission;
        this.key = key;
    }

    @Override
    public String getName() {
        return "hasPermission:" + key;
    }

    @Override
    public <T extends CommandSender> boolean validate(T sender) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(CC.colorize(Messages.NO_PERMISSION_MESSAGE));
            return false;
        }
        return true;
    }
}