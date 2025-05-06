package level;


import controller.MyGameController;
import gamestate.MyGameState;

public abstract class LevelBase {
    private MyGameState rGameState;

    public LevelBase(MyGameState gameState) {
        this.rGameState = gameState;
    }

    public abstract void levelInit() ;

    public void nextLevel() {

    }

    public void levelStart() {

    }

    public void levelDestroy() {

    }

    public MyGameState getrGameState() {
        return rGameState;
    }
}
