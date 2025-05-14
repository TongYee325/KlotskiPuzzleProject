package level;

import frame.*;
import gamestate.MyGameState;


public class MenuLevel extends LevelBase {
    private final int MENU_LEVEL_WIDTH = 600;
    private final int MENU_LEVEL_HEIGHT = 600;
    private final String MenuLevelText = "Menu Level";
    private MenuFrame menuFrame;
    private SelectFrame selectFrame;
    private MyGameState rGameState;
    private final String img1Path = "./img/menuFrame.png";
    private final String img2Path = "./img/selectFrame.png";
    private final String img3Path = "./img/settingFrame.png";

    public MenuLevel(MyGameState gameState) {
        super(gameState);
        rGameState = gameState;
        menuFrame = new  MenuFrame(this, MenuLevelText, MENU_LEVEL_WIDTH, MENU_LEVEL_HEIGHT,img1Path);
    }


    public void levelInit() {}

    public void switchToSelectFrame() {
        if(this.selectFrame == null) {
            this.selectFrame = new SelectFrame(this, MenuLevelText, MENU_LEVEL_WIDTH, MENU_LEVEL_HEIGHT);
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
        }
        if(selectFrame != null) {
            selectFrame.clear();
            selectFrame.dispose();
        }
    }

    public void switchToGameLevelAccordingToIndex(int index, boolean isTimedModeSelected){
        getrGameState().setGameMapIndex(index);
        getrGameState().startLevel(2);
    }
}
