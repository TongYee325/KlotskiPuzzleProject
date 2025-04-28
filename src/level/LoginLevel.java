package level;

import controller.MyGameController;
import frame.FrameBase;
import frame.LoginFrame;
import gamestate.MyGameState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginLevel extends LevelBase {
    private JFrame mainLoginFrame;
    private LoginFrame loginFrame;
    int a = 600;
    int b = 600;



    public LoginLevel(MyGameState gameState) {
        super(gameState);
        mainLoginFrame = new JFrame("登录选择");
        mainLoginFrame.setSize(600, 600);
        mainLoginFrame.setLocationRelativeTo(null);
        mainLoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加按钮等组件
        JButton guestLoginButton = new JButton("游客登录");
        guestLoginButton.addActionListener(e -> handleGuestLogin());
        mainLoginFrame.add(guestLoginButton);

        JButton userLoginButton = new JButton("注册/登录");
        userLoginButton.addActionListener(e -> handleUserLogin());
        mainLoginFrame.add(userLoginButton);

        mainLoginFrame.setLayout(new FlowLayout());
        mainLoginFrame.setVisible(true);
    }

    // 在LoginLevel类中添加游客登录处理
    private void handleGuestLogin() {
        mainLoginFrame.dispose(); // 关闭登录选择界面
/*
        String guestUserId = generateGuestUserId(); // 生成临时游客ID
*/
        //getrGameState().setCurrentUser(null); // 游客不需要ID，传入null
        nextLevel(); // 进入游戏主界面
    }
    private void handleUserLogin() {
        mainLoginFrame.dispose();
        loginFrame = new LoginFrame(this, getrGameState(),"用户登录/注册", a, b);
    }
    // 生成临时游客ID（格式：Guest_时间戳）
    private String generateGuestUserId() {
        long timestamp = System.currentTimeMillis();
        return "Guest_" + timestamp;
    }

    @Override
    public void levelInit() {
    }

    @Override
    public void nextLevel() {
        super.getrGameState().startLevel(1);//切换至下一Level
    }

    @Override
    public void levelDestroy() {
       // loginFrame.clear();
    }
}