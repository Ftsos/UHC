package me.ftsos.game.utils;

import me.ftsos.game.UhcGame;
import me.ftsos.utils.Callback;
import me.ftsos.utils.tasks.Task;

public abstract class GameTask extends Task {
    private UhcGame game;

    /**
    *
    * Abstract Class Handler of @Task
    *
    * */
    public GameTask(UhcGame game, Callback onRegisterCallback) {
        //Sending a void callback cause we're already executing it in other place
        super();
        this.game = game;
        this.game.getTaskHandler().register(this, onRegisterCallback);
    }

    public GameTask(UhcGame game) {
        //Sending a void callback cause we're already executing it in other place
        super();
        this.game = game;
        this.game.getTaskHandler().register(this, () -> {});
    }

    @Override
    public void cancel(Callback onUnregisterCallback) {
        super.cancel();
        this.game.getTaskHandler().unregister(this, onUnregisterCallback);
    }

    @Override
    public void cancel() {
        super.cancel();
        this.game.getTaskHandler().unregister(this, () -> {
            //Empty function as user didn't provide any callback
        });
    }
}
