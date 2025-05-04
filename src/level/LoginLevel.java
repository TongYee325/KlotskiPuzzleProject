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

        // 使用 GridBagLayout 布局
        mainLoginFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 添加按钮等组件
        JButton guestLoginButton = new JButton("游客登录");
        guestLoginButton.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainLoginFrame.add(guestLoginButton, gbc);

        JButton userLoginButton = new JButton("注册/登录");
        userLoginButton.setPreferredSize(new Dimension(200, 50)); // 设置按钮大小
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainLoginFrame.add(userLoginButton, gbc);

        guestLoginButton.addActionListener(e -> handleGuestLogin());
        userLoginButton.addActionListener(e -> handleUserLogin());

        mainLoginFrame.setVisible(true);
    }

    // 在LoginLevel类中添加游客登录处理
    private void handleGuestLogin() {
        mainLoginFrame.dispose(); // 关闭登录选择界面
        getrGameState().setCurrentUser(null);
        nextLevel(); // 进入游戏主界面
    }
    private void handleUserLogin() {
        mainLoginFrame.dispose();
        loginFrame = new LoginFrame(this, getrGameState(),"用户登录/注册", a, b);
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
        if(loginFrame != null) {
            loginFrame.clear();
        }
    }
}