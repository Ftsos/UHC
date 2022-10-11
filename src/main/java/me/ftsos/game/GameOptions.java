package me.ftsos.game;

public class GameOptions {
    private int minTeams;
    private int maxTeams;
    private int maxTeamSize;
    private String gameName;

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize, String gameName) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
        this.gameName = gameName;
    }

    public int getMinTeams() {
        return minTeams;
    }

    public int getMaxTeams() {
        return maxTeams;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public String getGameName() {
        return gameName;
    }
}
