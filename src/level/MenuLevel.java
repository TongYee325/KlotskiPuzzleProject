package level;

import frame.*;
import gamestate.MyGameState;


public class MenuLevel extends LevelBase {
    private final int MenuLevelWidth = 600;
    private final int MenuLevelHeight = 600;
    private final String MenuLevelText = "Menu Level";

    public MenuLevel(MyGameState gameState) {
        super(gameState);
        super.setrFrame(new MenuFrame(this, MenuLevelText, MenuLevelWidth, MenuLevelHeight));
    }


    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(2);//切换至下一Level
    }
}
