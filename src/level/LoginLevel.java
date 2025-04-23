package level;

import controller.MyGameController;
import frame.LoginFrame;
import gamestate.MyGameState;

public class LoginLevel extends LevelBase {
    private final int LoginLevelWidth = 600;
    private final int LoginLevelHeight = 600;
    private final String LoginLevelText = "Login Level";
    private LoginFrame loginFrame;

    public LoginLevel(MyGameState gameState) {
        super(gameState);
        loginFrame =new LoginFrame(this, LoginLevelText, LoginLevelWidth, LoginLevelHeight);
    }


    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(1);//切换至下一Level
    }

    @Override
    public void levelDestroy() {
        loginFrame.clear();
    }
}
