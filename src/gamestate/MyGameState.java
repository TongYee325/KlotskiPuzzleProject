package gamestate;

import level.*;

public class MyGameState extends GameStateBase {

    private int currentLevel = 0;
    private LevelBase level;

    public MyGameState() {

    }

    public void startLevel(int levelIndex){
        currentLevel = levelIndex;
        switch (currentLevel){
            case 0:
                level = new LoginLevel();
                LoginLevel loginLevel = (LoginLevel) level;
                loginLevel.LevelInit();
                break;
        }
    }



    public LevelBase getLevel() {
        return level;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

}
