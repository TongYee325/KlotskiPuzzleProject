package level;

import frame.LoginFrame;

public class LoginLevel extends LevelBase {
    private final int LoginLevelWidth=600;
    private final int LoginLevelHeight=600;
    private final String LoginLevelText="Login Level";
    private LoginFrame loginFrame;
    public LoginLevel() {
        super();
        loginFrame = new LoginFrame(LoginLevelText,LoginLevelWidth,LoginLevelHeight);


    }
    public void LevelInit(){
        loginFrame.update();
    }

}
