package level;

import controller.MyGameController;
import frame.GameFrame;
import gamestate.MyGameState;

import javax.swing.*;

public class GameLevel extends LevelBase {
    private final int GameLevelWidth = 600;
    private final int GameLevelHeight = 600;
    private final String GameLevelText = "Game Level";

    public GameMap getGameMap() {
        return gameMap;
    }

    private GameMap gameMap;

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    private GameFrame gameFrame;

    public MyGameController getController() {
        return gameController;
    }

    private MyGameController gameController;

    public GameLevel(MyGameState gameState) {
        super(gameState);
        gameController = new MyGameController(this);//为游戏关卡新建控制器
        gameMap =  new GameMap();
        gameFrame = new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight,gameMap);
        gameController.updateFrame();//更新游戏控制器，使其控制新建的Frame中的GamePanel
        gameFrame.initialGame();
    }

}
