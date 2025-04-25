package level;


import controller.MyGameController;
import gamestate.MyGameState;

public class LevelBase {
    private MyGameState rGameState;

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
