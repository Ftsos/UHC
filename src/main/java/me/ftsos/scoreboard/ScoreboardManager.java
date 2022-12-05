package me.ftsos.scoreboard;

import me.ftsos.UHC;
import me.ftsos.managers.Manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreboardManager extends Manager {
    private UHC plugin;
    private Map<Class<? extends Scoreboard>, Scoreboard> scoreboards;

    public ScoreboardManager(UHC plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public <T extends Scoreboard> T get(Class<T> clazz) {
        return clazz.cast(this.scoreboards.get(clazz));
    }

    public void registerScoreboard(Scoreboard scoreboard) {
        this.scoreboards.put(scoreboard.getClass(), scoreboard);
    }

    public List<Scoreboard> getScoreboards() {
        return this.scoreboards.values().stream().collect(Collectors.toList());
    }
}
