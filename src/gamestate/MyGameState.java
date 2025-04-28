package gamestate;

import Save.SaveManager;
import level.*;

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
        }
        if (level != null) {
            level.levelInit();
            level.levelStart();
        }
    }

    // 判断是否为游客用户
    public boolean isGuestUser() {
        return currentUserId == null || currentUserId.startsWith("Guest_");
    }

    public void loadGameData() {

    }


    public SaveManager getMySaveManager() {
        return mySaveManager;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    // 设置当前用户
    public void setCurrentUser(String userId) {
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
