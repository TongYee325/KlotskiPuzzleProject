package gamestate;

import controller.MyGameController;
import level.*;

public class MyGameState extends GameStateBase {
    private LogSystem myLogSystem;
    private String currentUserId; // 当前登录用户ID（游客或注册用户）
    private int currentLevel = 0;
    private LevelBase level;





    public MyGameState() {
        myLogSystem = new LogSystem();

    }

    public void startLevel(int levelIndex) {
        if (levelIndex == currentLevel && levelIndex != 0) {//传入关卡与当前关卡一样时，不做任何处理
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
        return currentUserId != null && currentUserId.startsWith("Guest_");
    }




    // 设置当前用户
    public void setCurrentUser(String userId) {
        this.currentUserId = userId;
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
