package me.ftsos.game.handlers;

import me.ftsos.events.UhcGameDeathEvent;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameTeam;
import me.ftsos.utils.Colorizer;
import me.ftsos.utils.config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameTeamHandler implements GameHandler {
    private List<GameTeam> gameTeams;
    private UhcGame uhcGame;

    public GameTeamHandler(UhcGame uhcGame) {
        this.uhcGame = uhcGame;
        this.gameTeams = new ArrayList<>();
    }

    public List<GameTeam> getGameTeams() {
        return gameTeams;
    }

    public void broadcastMessage(String message) {
        for(GamePlayer gPlayer : getPlayers()) {
            gPlayer.sendMessage(Colorizer.colorize(message));
        }
    }

    public List<GamePlayer> getPlayers(){
        List<GamePlayer> players = new ArrayList<>();
        for (GameTeam gameTeam : getGameTeams()) {
            for(GamePlayer gamePlayer : gameTeam.getPlayers()) {
                players.add(gamePlayer);
            }
        }

        return players;
    }

    public List<Player> getBukkitPlayers() {
        List<Player> players = new ArrayList<>();
        for(GamePlayer gPlayer : getPlayers()) {
            players.add(gPlayer.getPlayer());
        }
        return players;
    }

    public boolean lobbyContainsBukkitPlayer(Player player) {
        if(getTeam(player) != null) return true;
        return false;
    }

    public boolean lobbyContainsPlayer(GamePlayer player) {
        if(getTeam(player) != null) return true;
        return false;
    }

    public GameTeam getTeam(GamePlayer gamePlayer) {
        for(GameTeam gameTeam : this.getGameTeams()) {
            if(gameTeam.containsGamePlayer(gamePlayer)) return gameTeam;
        }
        return null;
    }

    public GameTeam getTeam(Player player) {
        for(GameTeam gameTeam : this.getGameTeams()) {
            if(gameTeam.containsPlayer(player)) return gameTeam;
        }
        return null;
    }


    public GamePlayer getGamePlayer(Player player) {
        GameTeam gameTeam = getTeam(player);
        if(gameTeam == null) return null;
        return gameTeam.getGamePlayer(player);
    }

    public void addTeam(GameTeam team) {
        if(team.getSize() > uhcGame.getGameOptions().getMaxTeamSize()) return;
        this.gameTeams.add(team);
    }

    @Override
    public void onWaiting() {
        //Teleport all players to spawn
        for(GamePlayer gPlayer : getPlayers()) {
            gPlayer.getPlayer().teleport(uhcGame.getMapHandler().getSpawnLocation());
        }
    }

    @Override
    public void onStarting() {

    }

    @Override
    public void onPlaying() {
        for(GameTeam team : this.gameTeams) {
            Location teamSpawnLocation = randomSpawnLocation();
            team.teleport(teamSpawnLocation);
        }

    }

    @Override
    public void onDeathmatch() {

    }

    @Override
    public void onFinishing() {

    }

    @Override
    public void onRestarting() {

    }

    public Location randomSpawnLocation() {
        int minX = -Config.SPAWN_RADIUS;
        int minZ = -Config.SPAWN_RADIUS;
        int maxX = Config.SPAWN_RADIUS;
        int maxZ = Config.SPAWN_RADIUS;
        Random rnd = new Random();

        int x = rnd.nextInt(maxX - minX + 1) + minX;
        int z = rnd.nextInt(maxZ - minZ + 1) + minZ;
        Location teamSpawnLocation = new Location(uhcGame.getMapHandler().getWorld(), x, uhcGame.getMapHandler().getWorld().getHighestBlockYAt(x, z), z);

        return teamSpawnLocation;
    }
}
