package gamestate;

import level.*;

public class MyGameState extends GameStateBase {

    private int currentLevel = 0;
    private LevelBase level;
    public MyGameState() {
        LevelBase level = new LevelBase();
    }

    public void startLevel(int levelIndex){
        currentLevel = levelIndex;
        switch (currentLevel){
            case 0:
                level = new LoginLevel();
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
