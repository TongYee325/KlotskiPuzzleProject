package level;

import frame.LoginFrame;
import gamestate.MyGameState;

public class LoginLevel extends LevelBase {
    private final int LoginLevelWidth = 600;
    private final int LoginLevelHeight = 600;
    private final String LoginLevelText = "Login Level";

    public LoginLevel(MyGameState gameState) {
        super(gameState);
        super.setrFrame(new LoginFrame(this, LoginLevelText, LoginLevelWidth, LoginLevelHeight));
    }

    @Override
    public void levelInit() {
        super.getrFrame().Init();
    }

    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(1);//切换至下一Level
    }

    @Override
    public void levelDestroy() {
        super.getrFrame().clear();
    }
}
