package gamestate;

import Save.GameSave;
import Save.SaveManager;
import level.*;

import java.util.ArrayList;

public class MyGameState extends GameStateBase {
    private LogSystem myLogSystem;


    private SaveManager mySaveManager;


    private String currentUserId; // 当前登录用户ID(null指游客)
    private int currentLevel = 0;
    private LevelBase level;


    public MyGameState() {
        myLogSystem = new LogSystem();
        mySaveManager = new SaveManager(this);

    }

    public void startLevel(int levelIndex) {
        if (levelIndex == currentLevel && levelIndex != 0) {
            //传入关卡与当前关卡一样时，不做任何处理
            return;
        }
        if (level != null) {
            level.levelDestroy();
            level = null;
        }
        currentLevel = levelIndex;
        switch (currentLevel) {
            case 0:
                level = new LoginLevel(this);
                break;
            case 1:
                level = new MenuLevel(this);
                break;
            case 2:
                level = new GameLevel(this);
                break;
        }
        if (level != null) {
            level.levelInit();
            level.levelStart();
        }
    }

    public void saveGameData(){
        mySaveManager.saveGame();
    }


    public void loadGameData() {
        GameSave gamesave = mySaveManager.loadGame();
        if (gamesave != null) {
            //将存档数据转化为可用数据
            int[][] panelMap = gamesave.getPanelMap();
            int levelIndex = gamesave.getCurrentLevelIndex();
            ArrayList<Step> totalSteps = gamesave.getTotalSteps();


            myLogSystem.setTotalSteps(totalSteps);
            if (levelIndex >= 2) {
                if (levelIndex == currentLevel) {
                    //传入关卡与当前关卡一样时，不做任何处理
                    return;
                }
                currentLevel = levelIndex;
                switch (currentLevel) {
                    case 2:
                        level = new GameLevel(this);
                        ((GameLevel) level).loadGame(panelMap);


                        break;
                        //todo add more level
                    default:
                        break;

                }
            }
        }
    }


    public SaveManager getMySaveManager() {
        return mySaveManager;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    // 设置当前用户
    public void setCurrentUser(String userId) {
        if(userId ==null) {
            currentUserId = null;
            return;
        }
        this.currentUserId = userId;
        mySaveManager.updateSavePathAccordingToUserName(userId);
    }

    public LevelBase getLevel() {
        return level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public LogSystem getMyLogSystem() {
        return myLogSystem;
    }

}
