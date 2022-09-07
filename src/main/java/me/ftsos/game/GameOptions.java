package me.ftsos.game;

public class GameOptions {
    private int minTeams;
    private int maxTeams;
    private int maxTeamSize;

    public GameOptions(int minTeams, int maxTeams, int maxTeamSize) {
        this.minTeams = minTeams;
        this.maxTeams = maxTeams;
        this.maxTeamSize = maxTeamSize;
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
}
