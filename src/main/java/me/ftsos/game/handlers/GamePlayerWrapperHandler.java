package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameSpectator;
import me.ftsos.game.players.GameTeam;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Messages;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GamePlayerWrapperHandler implements GameHandler{
    private UhcGame uhcGame;

    /**
    *
     * A Wrapper to hand all the players with other handlers
    *
    * */

    public GamePlayerWrapperHandler(UhcGame uhcGame) {
        this.uhcGame = uhcGame;
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {

    }

    public void playerAloneJoin(Player player) {
        //TODO: Make a option where players without a team can find one
        if((this.uhcGame.getGameTeamHandler().getGameTeams().size() + 1) > uhcGame.getGameOptions().getMaxTeams()) {
            player.sendMessage(CC.colorize(Messages.LOBBY_IS_FULL_MESSAGE));
            return;
        };

        if(uhcGame.getGameState() != GameState.WAITING) {
            player.sendMessage(CC.colorize(Messages.GAME_HAS_ALREADY_STARTED_JOINING_SPECTATOR_MODE_MESSAGE));
            this.uhcGame.getSpectatorHandler().addSpectator(new GameSpectator(player));
            return;
        };

        List<GamePlayer> gamePlayerList = Arrays.asList(new GamePlayer(player));
        GameTeam gameTeam = new GameTeam(gamePlayerList);
        player.sendMessage(CC.colorize(Messages.JOINING_TO_A_GAME_MESSAGE.replace("%gameName%", this.uhcGame.getGameOptions().getGameName())));
        this.uhcGame.getGameTeamHandler().teamJoin(gameTeam);
    }

    public void teamJoin(List<Player> players) {
        if(players.size() > uhcGame.getGameOptions().getMaxTeamSize()) {
            CC.colorizeMessageToPlayers(players, Messages.PARTY_IS_TOO_BIG_FOR_LOBBY_MESSAGE);
            return;
        }

        if((this.uhcGame.getGameTeamHandler().getGameTeams().size() + 1) > uhcGame.getGameOptions().getMaxTeams()) {
            CC.colorizeMessageToPlayers(players, Messages.LOBBY_IS_FULL_MESSAGE);
            return;
        };

        if(uhcGame.getGameState() != GameState.WAITING) {
            //TODO: Change this so party's can join spectator mode even if they are a party (right now you can't)
            CC.colorizeMessageToPlayers(players, Messages.GAME_HAS_ALREADY_STARTED_CANT_JOIN_SPECTATOR_MODE_MESSAGE);
            return;
        };

        List<GamePlayer> gamePlayers = players.stream().map(player -> new GamePlayer(player)).collect(Collectors.toList());
        GameTeam gameTeam = new GameTeam(gamePlayers);
        this.uhcGame.getGameTeamHandler().teamJoin(gameTeam);
        CC.colorizeMessageToPlayers(players, Messages.JOINING_TO_A_GAME_MESSAGE.replace("%gameName%", this.uhcGame.getGameOptions().getGameName()));
    }

}
