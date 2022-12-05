package me.ftsos.scoreboard.impl;

import dev.jcsoftware.jscoreboards.JPerPlayerMethodBasedScoreboard;

import dev.jcsoftware.jscoreboards.JScoreboardOptions;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.scoreboard.Scoreboard;
import me.ftsos.utils.CC;
import me.ftsos.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GameStateScoreboard implements Scoreboard<JPerPlayerMethodBasedScoreboard> {
    private UhcGame uhcGame;
    private GameState gameState;
    private JPerPlayerMethodBasedScoreboard scoreboard;

    public GameStateScoreboard(UhcGame uhcGame, GameState gameState) {
        this.uhcGame = uhcGame;
        //I let them tell me in which state they want the SC, just in case.
        this.gameState = gameState;
        if(gameState == GameState.RESTARTING) {
            this.scoreboard.destroy();
            return;
        }
        this.scoreboard = new JPerPlayerMethodBasedScoreboard();
        uhcGame.getGameTeamHandler().getBukkitPlayers().forEach(scoreboard::addPlayer);


        uhcGame.getGameTeamHandler().getBukkitPlayers().forEach(player -> {
            scoreboard.setTitle(player, CC.colorize(gameState.getScoreboardTitle()));

            List<String> lines = gameState.getScoreboard().stream().map(line -> {
                        String lineWithPlaceholdersReplaced = PlaceholderUtils.replacePlaceholders(getPlaceholders(uhcGame, player), line);
                        if(lineWithPlaceholdersReplaced.length() < 17) return CC.colorize(lineWithPlaceholdersReplaced);
                        String newLine = lineWithPlaceholdersReplaced;

                        if(newLine.charAt(16) != '&') {
                            List<String> colorMatches = new ArrayList<>();
                            Matcher colorMatcher = Pattern.compile("&[0-f0-F]")
                                    .matcher(newLine);
                            while (colorMatcher.find()) {
                                colorMatches.add(colorMatcher.group());
                            }
                            String toInsert = "&";
                            if(colorMatches.size() < 1) toInsert += "r";
                            if(colorMatches.size() > 0) {
                                String lastColorSet = colorMatches.get(colorMatches.size() - 1);
                                String lastColorCode = lastColorSet.charAt(1) + "";
                                toInsert += lastColorCode;

                            }
                            List<String> formatMatches = new ArrayList<>();
                            Matcher formatMatcher = Pattern.compile("&[k-rK-R]")
                                    .matcher(newLine);
                            while (formatMatcher.find()) {
                                formatMatches.add(formatMatcher.group());
                            }

                            if(formatMatches.size() > 0) {
                                toInsert += formatMatches.get(formatMatches.size() - 1);
                            }

                            StringBuffer newLineModifiedStillToAddColorBuffer = new StringBuffer(newLine);
                            newLineModifiedStillToAddColorBuffer.insert(17, toInsert);
                            newLine = CC.colorize(newLineModifiedStillToAddColorBuffer.toString());
                        }
                        return newLine.length() > 32 ? newLine.substring(0, 31) : newLine;
                    }).
                    collect(Collectors.toList());

            scoreboard.setLines(player, lines);
        });
        scoreboard.setOptions(JScoreboardOptions.defaultOptions);

    }

    @Override
    public JPerPlayerMethodBasedScoreboard getScoreboard() {
        return scoreboard;
    }

    //We let the creation of the task to the game, IF THE GAME ENDS THE SCOREBOARD HAS TO END no matter what
    public void updateScoreboard() {

        uhcGame.getGameTeamHandler().getBukkitPlayers().forEach(player -> {
            scoreboard.setTitle(player, CC.colorize(gameState.getScoreboardTitle()));
            List<String> lines = gameState.getScoreboard().stream().map(
                    line -> {
                        String lineWithPlaceholdersReplaced = PlaceholderUtils.replacePlaceholders(getPlaceholders(uhcGame, player), line);
                        if(lineWithPlaceholdersReplaced.length() < 17) return CC.colorize(lineWithPlaceholdersReplaced);
                        String newLine = lineWithPlaceholdersReplaced;

                        if(newLine.charAt(16) != '&') {
                            List<String> colorMatches = new ArrayList<>();
                            Matcher colorMatcher = Pattern.compile("&[0-f0-F]")
                                    .matcher(newLine);
                            while (colorMatcher.find()) {
                                colorMatches.add(colorMatcher.group());
                            }
                            String toInsert = "&";
                            if(colorMatches.size() < 1) toInsert += "r";
                            if(colorMatches.size() > 0) {
                                String lastColorSet = colorMatches.get(colorMatches.size() - 1);
                                String lastColorCode = lastColorSet.charAt(1) + "";
                                toInsert += lastColorCode;

                            }
                            List<String> formatMatches = new ArrayList<>();
                            Matcher formatMatcher = Pattern.compile("&[k-rK-R]")
                                    .matcher(newLine);
                            while (formatMatcher.find()) {
                                formatMatches.add(formatMatcher.group());
                            }

                            if(formatMatches.size() > 0) {
                                toInsert += formatMatches.get(formatMatches.size() - 1);
                            }

                            StringBuffer newLineModifiedStillToAddColorBuffer = new StringBuffer(newLine);
                            newLineModifiedStillToAddColorBuffer.insert(16, toInsert);
                            newLine = newLineModifiedStillToAddColorBuffer.toString();
                        }
                        newLine = CC.colorize(newLine);
                        return newLine.length() > 32 ? newLine.substring(0, 31) : newLine;
                    }
            ).collect(Collectors.toList());

            scoreboard.setLines(player, lines);
        });

        this.scoreboard.updateScoreboard();

    }

    public Map<String, String> getPlaceholders(UhcGame uhcGame, Player player) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("%inGamePlayers%", uhcGame.getGameTeamHandler().getBukkitPlayers().size() + "");
        placeholders.put("%playerName%", player.getName());
        placeholders.put("%gameName%", uhcGame.getGameOptions().getDisplayName());
        placeholders.put("%gameState%", uhcGame.getGameState().getDisplayName());
        placeholders.put("%maxTeamsInGame%", uhcGame.getGameOptions().getMaxTeams() + "");
        placeholders.put("%minTeamsRequiredToStartGame%", uhcGame.getGameOptions().getMinTeams() + "");
        placeholders.put("%time%", uhcGame.getTimeHandler().getTime() + "");
        placeholders.put("%inGameTeams%", uhcGame.getGameTeamHandler().getGameTeams().size() + "");
        placeholders.put("%teamSize%", uhcGame.getGameOptions().getMaxTeamSize() + "");
        return placeholders;
    }

    public void destroyScoreboard() {
        this.scoreboard.destroy();
    }

    public void playerJoin(Player player) {
        this.scoreboard.addPlayer(player);
    }

    public void playerLeave(Player player) {
        this.scoreboard.removePlayer(player);
    }
}
