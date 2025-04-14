package level;

import frame.LoginFrame;

public class LoginLevel extends LevelBase {
    private final int LoginLevelWidth=600;
    private final int LoginLevelHeight=600;
    private final String LoginLevelText="Login Level";
    public LoginLevel() {
        frame = new LoginFrame(LoginLevelText,LoginLevelWidth,LoginLevelHeight);
    }
}
