package me.ftsos.game.handlers;

public interface GameHandler {
    void onWaiting();
    void onStarting();
    void onPlaying();
    void onDeathmatch();
    void onFinishing();
    void onRestarting();
}
