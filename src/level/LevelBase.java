package level;


import frame.FrameBase;
import gamestate.MyGameState;

public class LevelBase {
    private MyGameState rGameState;

    public LevelBase(MyGameState gameState) {
        rGameState = gameState;
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
