package level;


import controller.MyGameController;
import gamestate.MyGameState;

public class LevelBase {
    private MyGameState rGameState;
    protected MyGameController rGameController;

    public LevelBase(MyGameState gameState, MyGameController gameController) {

        this.rGameState = gameState;
        this.rGameController = gameController;
    }

    public LevelBase(MyGameState gameState) {
        this.rGameState = gameState;
    }

    public void levelInit() {

    }

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
