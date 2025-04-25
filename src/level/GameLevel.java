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
    private MyGameController gameController;



    public GameLevel(MyGameState gameState) {
        super(gameState);
        gameController = new MyGameController(this,gameState.getMyLogSystem());//为游戏关卡新建控制器
        gameMap =  new GameMap();//创建游戏地图
        gameFrame = new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight,gameMap);//创建游戏帧
        gameController.updateControlledPanelAccordingToLevel();//更新游戏控制器，使其控制新建的Frame中的GamePanel*/
        this.initialGame();//初始化游戏
    }


    private void initialGame() {
        gameFrame.initialGame();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public MyGameController getController() {
        return gameController;
    }
}
