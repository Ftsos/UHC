package me.ftsos.game.handlers;

import me.ftsos.events.game.GameStateUpdateEvent;
import me.ftsos.game.GameState;
import me.ftsos.game.UhcGame;
import me.ftsos.game.players.GamePlayer;
import me.ftsos.game.players.GameSpectator;
import me.ftsos.game.players.GameTeam;
import me.ftsos.game.utils.GameTask;
import me.ftsos.utils.Callback;
import me.ftsos.utils.CC;
import me.ftsos.utils.config.Config;
import me.ftsos.utils.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameTeamHandler implements GameHandler {
    private List<GameTeam> gameTeams;
    private UhcGame uhcGame;

    /**
     * @GameTeamHandler
     * This class will handle all the stuff related to teams/players internally for external methods is @GamePlayerWrapperHandler
    * */

    public GameTeamHandler(UhcGame uhcGame) {
        this.uhcGame = uhcGame;
        this.gameTeams = new ArrayList<>();
    }

    public List<GameTeam> getGameTeams() {
        return gameTeams;
    }

    public void broadcastMessage(String message) {
        for(GamePlayer gPlayer : getPlayers()) {
            gPlayer.sendMessage(CC.colorize(message));
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
            gPlayer.getPlayer().ifPresent(player -> {
                players.add(player);
            });
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

    public void teamJoin(GameTeam team) {
        //Team bigger than max size of the allowed for the game check
        if(team.getSize() > uhcGame.getGameOptions().getMaxTeamSize()) return;
        //Game is already full
        if((getGameTeams().size() + 1) > uhcGame.getGameOptions().getMaxTeams()) return;
        //Game has already started
        if(uhcGame.getGameState() != GameState.WAITING) return;

        //Adding the team
        this.gameTeams.add(team);

        for(GamePlayer gPlayer : team.getPlayers()) {
            gPlayer.getPlayer().ifPresent(player -> {
                playerJoinToGameInWaitingState(player);
            });
        }

        if((getGameTeams().size()) >= uhcGame.getGameOptions().getMinTeams()) {
            this.uhcGame.updateGameState(GameState.STARTING);
        }
    }

    public void onPlayerGetKilled(GameTeam team, GamePlayer gPlayer) {
        gPlayer.getPlayer().ifPresent(player -> {
            this.uhcGame.getSpectatorHandler().addSpectator(new GameSpectator(player));
        });
        team.onPlayerKilled(gPlayer);
        if(!team.getPlayers().isEmpty()) return;
        if((this.gameTeams.size() - 1) > 1) {
            this.gameTeams.remove(team);
            return;
        }

        this.uhcGame.updateGameState(GameState.FINISHING);
    }

    public void onWaiting() {
        //Teleport all players to spawn
        //Will be called at the very start of the game, no one should be there but just in case
        for(GamePlayer gPlayer : getPlayers()) {
            gPlayer.getPlayer().ifPresent(player -> {
                playerJoinToGameInWaitingState(player);
            });
        }
    }


    public void onStarting() {
        //Call the countdown
        startStartingCountdown(() -> {
            //Called when the countdown has ended
            for(Player player : getBukkitPlayers()) {
                //Clean up the player title and subtitle
                player.sendTitle("", "");
            }

            //Move to the next game state
            uhcGame.updateGameState(GameState.PLAYING);
        });
    }

    public void onPlaying() {
        //TODO: Send Messages & Titles for playing state
        for(GameTeam team : this.gameTeams) {
            Location teamSpawnLocation = randomSpawnLocation();
            team.teleport(teamSpawnLocation);
        }

    }

    public void onDeathmatch() {

    }

    public void onFinishing() {
        if(this.gameTeams.size() > 0) {
            List<String> playersNameList = new ArrayList<>();
            this.gameTeams.get(0).getPlayers().forEach(gamePlayer -> {
                if(gamePlayer.getPlayer().isPresent()) {
                    playersNameList.add(Messages.GAME_TEAM_WON_GAME_PLAYER_NAME_DISPLAY_FORMAT.replace("%gamePlayerName%", gamePlayer.getPlayer().get().getDisplayName()));
                } else {
                    playersNameList.add(Messages.GAME_TEAM_WON_OFFLINE_GAME_PLAYER_NAME_DISPLAY_FORMAT.replace("%gamePlayerName%", gamePlayer.getPlayer().get().getDisplayName()));
                }
            });

            String playersOnTeamNames = playersNameList.stream().collect(Collectors.joining(Messages.GAME_TEAM_WON_GAME_PLAYER_NAME_DISPLAY_CONCATENATOR_FORMAT));
            this.uhcGame.broadcastMessage(CC.colorize(Messages.GAME_TEAM_WON_MESSAGE.replace("%gamePlayerNames%", playersOnTeamNames)));
        } else {
            this.uhcGame.broadcastMessage(Messages.TIED_GAME_MESSAGE);
        }

        //TODO: Wait some seconds and send fireworks, titles (to players and specs), add stats, and stuff
        this.uhcGame.updateGameState(GameState.RESTARTING);
    }

    public void onRestarting() {
        for(GamePlayer gamePlayer : this.getPlayers()) {
                if(gamePlayer.getPlayer().isPresent()) {
                    gamePlayer.sendMessage(CC.colorize(Messages.GAME_FINISHED_TELEPORTING_TO_LOBBY_MESSAGE));
                    gamePlayer.getPlayer().ifPresent(player -> {
                        //Teleporting all players back to lobby
                        player.teleport(this.uhcGame.getLobby().getLobbyWorldHandler().getSpawnLocation());
                    });
                    continue;
                }
                //If player is a offline player
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(gamePlayer.getPlayerUUID());
                if(offlinePlayer.isOnline()) {
                    offlinePlayer.getPlayer().teleport(this.uhcGame.getLobby().getLobbyWorldHandler().getSpawnLocation());
                }
                //TODO: Teleport the players even if they are offline players
        }

        this.gameTeams.clear();
        this.gameTeams = new ArrayList<>();
    }

    @Override
    public void onGameStateUpdate(GameStateUpdateEvent event) {
        if(event.isCancelled()) return;

        // Called on Waiting State Firing
        if(event.getNewGameState() == GameState.WAITING) {
            onWaiting();
        }

        // Called on Starting State Firing
        if(event.getNewGameState() == GameState.STARTING) {
            onStarting();
        }

        // Called on Playing State Firing
        if(event.getNewGameState() == GameState.PLAYING) {
            onPlaying();
        }

        // Called on Deathmatch State Firing
        if(event.getNewGameState() == GameState.DEATHMATCH) {
            onDeathmatch();
        }

        // Called on Finishing State Firing
        if(event.getNewGameState() == GameState.FINISHING) {
            onFinishing();
        }

        // Called on Restarting State Firing
        if(event.getNewGameState() == GameState.RESTARTING) {
            onRestarting();
        }
    }

    /*
    * Utils for State Updates Functions
    * */

    /*
    * Will return a random location between the spawn radius
    * Used for getting all the teams spawns
    * */
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

    public void playerJoinToGameInWaitingState(Player player) {
        //Clearing players inventories, clearing players titles, setting gamemodes, removing hunger, velocities and fire;
        player.setGameMode(GameMode.SURVIVAL);
        player.sendTitle("", "");
        player.setHealth(player.getMaxHealth());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setVelocity(new Vector(0, 0, 0));
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setFallDistance(0);

        //Teleporting players to Spawn
        player.teleport(uhcGame.getMapHandler().getSpawnLocation());
    }


    /*
    * Will start starting countdown for all players
    * */
    public void startStartingCountdown(Callback onCountdownEnds) {
        /*
        * Java will apparently not let me use a simple variable here because "Variable is accessed within inner class. Needs to be declared final"
        * So found this lil bit hacky solution, but hey man, if it works it works
        * https://stackoverflow.com/questions/14425826/variable-is-accessed-within-inner-class-needs-to-be-declared-final#answer-49738591
        * */
        final int[] seconds = {Config.STARTING_COUNTDOWN_TIMER};

        GameTask countdownRunnable = new GameTask(this.uhcGame) {
            @Override
            public void runCustomTask() {
                if((seconds[0] - 1) < 0) this.cancel(onCountdownEnds);
                for(Player player : getBukkitPlayers()) {
                    /*
                     * TODO: Feature Request: Add a subtitle related to every numbers
                     * */
                    player.sendTitle(CC.colorize(Messages.STARTING_COUNTDOWN_NUMBER_TITLE.replace("%timer%", seconds + "")), "");
                    player.sendMessage(CC.colorize(Messages.STARTING_COUNTDOWN_NUMBER_MESSAGE.replace("%timer%", seconds + "")));
                }
                seconds[0] = seconds[0] - 1;
            }
        };

        //Run the starting countdown every second until stops
        countdownRunnable.runTaskTimer(0L, 20L);

    }
}

