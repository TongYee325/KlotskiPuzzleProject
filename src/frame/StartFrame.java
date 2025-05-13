package frame;

import level.LevelBase;
import level.LoginLevel;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends FrameBase {
    private LoginLevel rLevel;
    private final String guestPath = "./img/button/guest.png";
    private final String guestRolloverPath = "./img/button/guest_rollover.png";
    private final String guestPressedPath = "./img/button/guest_pressed.png";
    private final String loginPath = "./img/button/login.png";
    private final String loginRolloverPath = "./img/button/login_rollover.png";
    private final String loginPressedPath = "./img/button/login_pressed.png";





    public StartFrame(LevelBase level, String title, int width, int height,String imgPath) {
        super(level, title, width, height);
        this.rLevel = ((LoginLevel) level);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setVgap(20);
        JPanel mainPanel = new JPanel(gridLayout);
        mainPanel.setOpaque(false);
        mainPanel.setBounds(width/2-400/2, height/4+50, 150, 140);

        // 添加按钮等组件
        JButton guestLoginButton = new JButton("Visitor Login");
        guestLoginButton.setPreferredSize(new Dimension(150, 65)); // 设置按钮大小
        super.setButtonBackground(guestLoginButton,guestPath,guestRolloverPath,guestPressedPath);

        JButton userLoginButton = new JButton("Login Register");
        userLoginButton.setPreferredSize(new Dimension(150, 65)); // 设置按钮大小
        super.setButtonBackground(userLoginButton,loginPath,loginRolloverPath,loginPressedPath);
        guestLoginButton.addActionListener(e -> handleGuestLogin());
        userLoginButton.addActionListener(e -> handleUserLogin());

        this.setVisible(true);
        this.add(mainPanel);
        mainPanel.add(guestLoginButton);
        mainPanel.add(userLoginButton);


        super.setBackground(imgPath);
    }
        /*mainLoginFrame = new JFrame("登录选择");
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
    }*/

    // 在LoginLevel类中添加游客登录处理
    private void handleGuestLogin() {
        this.dispose(); // 关闭登录选择界面
        rLevel.getrGameState().setCurrentUser(null);
        rLevel.nextLevel(); // 进入游戏主界面
    }
    private void handleUserLogin() {
        this.dispose();
        rLevel.switchToLoginFrame();

    }
}
