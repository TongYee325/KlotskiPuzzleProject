package level;

import frame.LoginFrame;
import gamestate.MyGameState;

public class LoginLevel extends LevelBase {
    private final int LoginLevelWidth=600;
    private final int LoginLevelHeight=600;
    private final String LoginLevelText="Login Level";
    private LoginFrame loginFrame;
    private MyGameState rGameState;

    public LoginLevel(MyGameState gameState) {
        super();
        rGameState=gameState;
        loginFrame = new LoginFrame(this,LoginLevelText,LoginLevelWidth,LoginLevelHeight);
    }
    @Override
    public void levelInit(){
        loginFrame.Init();
    }
    @Override
    public void nextLevel(){
        rGameState.startLevel(1);//切换至下一Level
    }
    @Override
    public void levelDestroy(){
        loginFrame.clear();
    }
}
