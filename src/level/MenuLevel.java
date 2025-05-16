package level;

import frame.*;
import gamestate.MyGameState;


public class MenuLevel extends LevelBase {
    private final int MENU_LEVEL_WIDTH = 600;
    private final int MENU_LEVEL_HEIGHT = 600;
    private final String MenuLevelText = "Menu Level";
    private MenuFrame menuFrame;
    private SelectFrame selectFrame;
    private SettingFrame settingsFrame;
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
            this.selectFrame = new SelectFrame(this, MenuLevelText, MENU_LEVEL_WIDTH, MENU_LEVEL_HEIGHT,img2Path);
        }
        this.selectFrame.setVisible(true);
        this.selectFrame.setEnabled(true);
        this.menuFrame.setVisible(false);
        this.menuFrame.setEnabled(false);
    }

    public void switchToMenuFrame() {
        this.menuFrame.setVisible(true);
        this.menuFrame.setEnabled(true);
        if(this.selectFrame != null) {
            this.selectFrame.setVisible(false);
            this.selectFrame.setEnabled(false);
            this.selectFrame.dispose();
        }
        if(this.settingsFrame != null) {
            this.settingsFrame.setVisible(false);
            this.settingsFrame.setEnabled(false);
            this.settingsFrame.dispose();
        }
    }

    public void switchToSettingFrame() {
        if(this.settingsFrame == null) {
            this.settingsFrame = new SettingFrame(this,MenuLevelText, MENU_LEVEL_WIDTH, MENU_LEVEL_HEIGHT,img3Path);
        }
        this.menuFrame.setVisible(false);
        this.menuFrame.setEnabled(false);
        this.settingsFrame.setVisible(true);
        this.settingsFrame.setEnabled(true);
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
