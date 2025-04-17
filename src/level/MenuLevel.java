package level;

import frame.*;
import gamestate.MyGameState;


public class MenuLevel extends LevelBase {
    private final int MenuLevelWidth=600;
    private final int MenuLevelHeight=600;
    private final String MenuLevelText="Menu Level";
    private MenuFrame menuFrame;
    private MyGameState rGameState;

    public MenuLevel(MyGameState gameState) {
        super();
        rGameState=gameState;
        menuFrame = new MenuFrame(this,MenuLevelText,MenuLevelWidth, MenuLevelHeight);
    }
    @Override
    public void levelInit(){
        menuFrame.update();
    }
    @Override
    public void nextLevel(){
        rGameState.startLevel(1);//切换至下一Level
    }
}
