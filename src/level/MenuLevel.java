package level;

import frame.*;
import gamestate.MyGameState;


public class MenuLevel extends LevelBase {
    private final int MenuLevelWidth = 600;
    private final int MenuLevelHeight = 600;
    private final String MenuLevelText = "Menu Level";
    private MenuFrame menuFrame;
    private MyGameState rGameState;

    public MenuLevel(MyGameState gameState) {
        super(gameState);
        rGameState = gameState;
        menuFrame = new  MenuFrame(this, MenuLevelText, MenuLevelWidth, MenuLevelHeight);
    }


    public void levelInit() {}

    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(2);//切换至下一Level
    }

    @Override
    public void levelDestroy() {
        if(menuFrame != null) {
            menuFrame.clear();
        }
    }
}
