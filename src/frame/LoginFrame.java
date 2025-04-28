package frame;


import level.AccountManager;
import level.LevelBase;
import level.LoginLevel;
import gamestate.MyGameState;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LoginFrame extends FrameBase {
    private final MyGameState gameState;
    private JTextField username;
    private JPasswordField password;

    private JButton submitBtn;
    private JButton registerBtn;
    private JPanel textPanel;

    public LoginFrame(LoginLevel loginLevel, MyGameState gameState, String title, int width, int height) {
        super(loginLevel, title, width, height);
        this.gameState = gameState;
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point userLocation = new Point(center.x - width / 4, center.y - height / 3);
        Point passwordLocation = new Point(center.x - width / 4, center.y - height / 5);
        JLabel userLabel = FrameUtil.createJLabel(this, userLocation, 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, passwordLocation, 70, 40, "password:");
        this.add(userLabel);
        this.add(passLabel);
        //todo:add hint test box

        username = FrameUtil.createJTextField(this, new Point((int) (userLocation.getX() + userLabel.getWidth()), (int) userLocation.getY()), 200, 40);
        password = FrameUtil.createJPasswordField(this, new Point((int) (passwordLocation.getX() + passLabel.getWidth()), (int) passwordLocation.getY()), 200, 40);
        Random random = new Random();
        if(random.nextBoolean()){password.setEchoChar('*');}//小trick
        this.add(username);
        this.add(password);


        Point buttonLocation = new Point(center.x, center.y);
        submitBtn = FrameUtil.createButton(this, "Confirm", new Point((int) (buttonLocation.getX() - width / 4.0), (int) buttonLocation.getY()), 100, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point((int) (buttonLocation.getX() + width / 4.0 - submitBtn.getWidth()), (int) buttonLocation.getY()), 100, 40);
        this.add(registerBtn);
        this.add(submitBtn);

        submitBtn.addActionListener(e -> {

            if (AccountManager.checkPassword(username.getText(), password.getPassword())) {//检查用户名与密码
                if(username.getText()!=null){getRlevel().getrGameState().setCurrentUser(username.getText());}//存入当前userID
                LoginLevel rlevel = (LoginLevel) super.getRlevel();
                rlevel.nextLevel();
            } else {
                System.out.println("Wrong Username or Password");
            }
        });
        registerBtn.addActionListener(e -> {
            System.out.println(AccountManager.registerAccount(username.getText(), password.getPassword()));//注册
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }



    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getResetBtn() {
        return registerBtn;
    }

}
