package level;

import frame.*;
import gamestate.MyGameState;

import java.awt.*;


public class MenuLevel extends LevelBase {
    private final int MenuLevelWidth = 600;
    private final int MenuLevelHeight = 600;
    private final String MenuLevelText = "Menu Level";
    private MenuFrame menuFrame;
    private SelectFrame selectFrame;
    private MyGameState rGameState;

    public MenuLevel(MyGameState gameState) {
        super(gameState);
        rGameState = gameState;
        menuFrame = new  MenuFrame(this, MenuLevelText, MenuLevelWidth, MenuLevelHeight);
    }


    public void levelInit() {}

    public void switchToSelectFrame() {
        if(this.selectFrame == null) {
            this.selectFrame = new SelectFrame(this, MenuLevelText, MenuLevelWidth, MenuLevelHeight);
        }
        this.selectFrame.setVisible(true);
        this.selectFrame.setEnabled(true);
        this.menuFrame.setVisible(false);
        this.menuFrame.setEnabled(false);
    }

    public void switchToMenuFrame() {
        this.menuFrame.setVisible(true);
        this.menuFrame.setEnabled(true);
        this.selectFrame.setVisible(false);
        this.selectFrame.setEnabled(false);
    }

    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(2);//切换至下一Level
    }

    @Override
    public void levelDestroy() {
        if(menuFrame != null) {
            menuFrame.clear();
            menuFrame.dispose();
            selectFrame.clear();
            selectFrame.dispose();
        }
    }

    public void switchToGameLevelAccordingToIndex(int index){
        getrGameState().setGameMapIndex(index);
        getrGameState().startLevel(2);
    }
}
