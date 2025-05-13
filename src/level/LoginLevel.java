package level;

import controller.MyGameController;
import frame.FrameBase;
import frame.LoginFrame;
import frame.StartFrame;
import gamestate.MyGameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginLevel extends LevelBase {
    private StartFrame mainLoginFrame;
    private LoginFrame loginFrame;
    private final String img1Path = "./img/startFrame.png";
    private final String img2Path = "./img/loginFrame.png";

    int a = 600;
    int b = 600;



    public LoginLevel(MyGameState gameState) {
        super(gameState);
    }

    @Override
    public void levelInit() {
        this.mainLoginFrame = new StartFrame(this,"Welcome to play",600,600,img1Path);
    }

    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(1);//切换至下一Level
    }

    public void switchToLoginFrame(){
        loginFrame = new LoginFrame(this, getrGameState(),"Login/Register", a, b,img2Path);
    }

    @Override
    public void levelDestroy() {
        if(loginFrame != null) {
            loginFrame.clear();
        }
    }
}