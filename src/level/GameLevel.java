package level;

import controller.MyGameController;
import frame.GameFrame;
import gamestate.MyGameState;

public class GameLevel extends LevelBase {
    private final int GameLevelWidth = 600;
    private final int GameLevelHeight = 600;
    private final String GameLevelText = "Game Level";
    private GameMap gameMap;
    private GameFrame gameFrame;

    public GameLevel(MyGameState gameState) {
        super(gameState);
        gameMap =  new GameMap();

        gameFrame = new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight,gameMap);
        gameFrame.initialGame();
        MyGameController gameController = new MyGameController(gameFrame.getGamePanel(),gameMap);
    }

}
