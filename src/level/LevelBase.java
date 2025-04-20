package level;


import frame.FrameBase;
import gamestate.MyGameState;

public class LevelBase {
    private MyGameState rGameState;


    private FrameBase rFrame;

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

    public void setrFrame(FrameBase rFrame) {
        this.rFrame = rFrame;
    }

    public FrameBase getrFrame() {
        return rFrame;
    }

    public MyGameState getrGameState() {
        return rGameState;
    }
}
