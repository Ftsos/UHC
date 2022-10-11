package me.ftsos.commands.commands.uhc.arguments;

import me.ftsos.commands.utils.Argument;
import me.ftsos.game.GameOptions;
import org.bukkit.command.CommandSender;

public class GameOptionsArgument implements Argument {

    /**
     * This command argument represents a GameOptions object.
     * The argument should be parsed like this: <minTeams>;<maxTeams>;<maxTeamSize>;<gameName>
     * Usage: /uhc start <minTeams>;<maxTeams>;<maxTeamSize>;<gameName> <announce>
     * Example: /uhc start 5;10;3;Rooting
     */

    public GameOptionsArgument() {
    }

    //&cInvalid Use. Please use the arguments in the following way &7<minTeams>;<maxTeams>;<maxTeamSize>;<gameName>
    //&aUsage: &8/uhc start <minTeams>;<maxTeams>;<maxTeamSize>;<gameName>
    @Override
    public <T extends CommandSender> Object validate(T sender, String argument) {
        String[] properties = argument.split(";");
        if (properties.length != 4) {
            return null;
        }

        int minTeams = 0;
        try {
            minTeams = Integer.parseInt(properties[0]);
        } catch (NumberFormatException e) {
            return null;
        }

        int maxTeams = 0;
        try {
            maxTeams = Integer.parseInt(properties[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        int maxTeamSize = 0;
        try {
            maxTeamSize = Integer.parseInt(properties[2]);
        } catch (NumberFormatException e) {
            return null;
        }

        String gameName = properties[3];

        GameOptions gameOptions = new GameOptions(minTeams, maxTeams, maxTeamSize, gameName);
        return gameOptions;
    }
}
