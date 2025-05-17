package level;

import audio.AudioManager;
import controller.MyGameController;
import frame.GameFrame;
import gamemode.AiGameMode;
import gamemode.GameModeBase;
import gamestate.MyGameState;
import level.map.GameMap;

import javax.swing.*;

public class GameLevel extends LevelBase {

    private final int GameLevelWidth = 600;
    private final int GameLevelHeight = 600;
    private final String GameLevelText = "Game Level";
    private GameMap gameMap;
    private GameFrame gameFrame;
    private Timer saveTimer;
    private MyGameState rGameState;



    private GameModeBase rGameModeBase;

    private int gameMapIndex;


    private MyGameController gameController;






    public GameLevel(MyGameState gameState) {
        super(gameState);
        this.rGameState = gameState;
        gameController = new MyGameController(this,gameState.getMyLogSystem());//为游戏关卡新建控制器
        gameMap =  new GameMap();//创建游戏地图
        gameFrame = new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight,gameMap);//创建游戏帧
        gameController.updateControlledPanelAccordingToLevel();//更新游戏控制器，使其控制新建的Frame中的GamePanel*/
        if(rGameState.isAutoSave()&&rGameState.getCurrentUserId()!=null){
            saveTimer = new Timer( rGameState.getSaveTime(), e -> {saveGame();});//自动保存，默认30s保存一次，用户可修改
        }



        //选择游戏模式
        //测试阶段默认aimode
        rGameModeBase = new AiGameMode(this);

    }

    public GameLevel(MyGameState gameState,int gameMapIndex) {
        super(gameState);
        this.gameMapIndex = gameMapIndex;
        this.rGameState = gameState;
        gameController = new MyGameController(this,gameState.getMyLogSystem());//为游戏关卡新建控制器
        gameMap =  new GameMap(gameMapIndex);//创建游戏地图
        gameFrame = new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight,gameMap);//创建游戏帧
        gameController.updateControlledPanelAccordingToLevel();//更新游戏控制器，使其控制新建的Frame中的GamePanel*/
        if(rGameState.isAutoSave()&&rGameState.getCurrentUserId()!=null){
            saveTimer = new Timer( rGameState.getSaveTime(), e -> {saveGame();});//自动保存，默认30s保存一次，用户可修改
        }



        //选择游戏模式
        rGameModeBase = new AiGameMode(this);

    }



    public void levelInit() {
        gameFrame.initialGame();
        if(saveTimer!=null){
            saveTimer.start();
        }
    }

    public void saveGame() {
        gameFrame.showSaveInfo();
        rGameState.saveGameData();
    }

    public void loadGame(int[][] panelMap) {
        gameFrame.initialGame(panelMap);
        if(saveTimer!=null){
            saveTimer.start();
        }
    }


    public GameModeBase getrGameModeBase() {
        return rGameModeBase;
    }

    public MyGameController getGameController() {
        return gameController;
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

    public void levelDestroy() {
        if(saveTimer!=null) {
            saveTimer.stop();
        }
        this.gameFrame.dispose();
    }
}
