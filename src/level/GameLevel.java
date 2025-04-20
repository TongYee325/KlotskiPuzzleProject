package level;

import frame.GameFrame;
import gamestate.MyGameState;

public class GameLevel extends LevelBase {
    private final int GameLevelWidth = 600;
    private final int GameLevelHeight = 600;
    private final String GameLevelText = "Game Level";

    //todo
    public GameLevel(MyGameState gameState) {
        super(gameState);
        super.setrFrame(new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight));
    }

}
