package gamestate;

import level.*;

public class MyGameState extends GameStateBase {

    private int currentLevel = 0;
    private LevelBase level;

    public MyGameState() {

    }

    public void startLevel(int levelIndex){
        if(levelIndex == currentLevel&&levelIndex!=0){//传入关卡与当前关卡一样时，不做任何处理
            return;
        }
        if(level!=null){
            level.levelDestroy();
            level = null;
        }
        currentLevel = levelIndex;
        switch (currentLevel){
            case 0:
                level = new LoginLevel(this);
                break;
                case 1:
                    level = new MenuLevel(this);
                    break;
                    case 2:
                        level =new GameLevel(this);
        }
        if(level!=null){
            level.levelInit();
            level.levelStart();
        }
    }



    public LevelBase getLevel() {
        return level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

}
