package frame;


import frame.dialog.DefaultDialog;
import level.AccountManager;
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
    private JDialog dialog;

    private final String loginPath = "./img/button/login.png";
    private final String loginRolloverPath = "./img/button/login_rollover.png";
    private final String loginPressedPath = "./img/button/login_pressed.png";

    private final String registerPath = "./img/button/register.png";
    private final String registerRolloverPath = "./img/button/register_rollover.png";
    private final String registerPressedPath = "./img/button/register_pressed.png";

    public LoginFrame(LoginLevel loginLevel, MyGameState gameState, String title, int width, int height, String imgPath) {
        super(loginLevel, title, width, height);
        this.gameState = gameState;
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point userLocation = new Point(center.x + 10 - width / 2, center.y - height / 3);
        Point passwordLocation = new Point(center.x, center.y - height / 3);
        JLabel userLabel = FrameUtil.createJLabel(this, userLocation, 70, 40, "Username:");
        JLabel passLabel = FrameUtil.createJLabel(this, passwordLocation, 70, 40, "Password:");
        this.add(userLabel);
        this.add(passLabel);

        //提示框
        int hintX = (width - 450) / 2;
        int hintY = 20;
        Point hintLocation = new Point(hintX, hintY);
        JLabel hintLabel = FrameUtil.createJLabel(this, hintLocation, 500, 40, "The username and password are limited to a-z A-Z 0-9 _ -, and the quantity is 3-20");
        this.add(hintLabel);

        username = FrameUtil.createJTextField(this, new Point((int) (userLocation.getX() + userLabel.getWidth()), (int) userLocation.getY()), 200, 40);
        password = FrameUtil.createJPasswordField(this, new Point((int) (passwordLocation.getX() + passLabel.getWidth()), (int) passwordLocation.getY()), 200, 40);
        Random random = new Random();
        if (random.nextBoolean()) {
            password.setEchoChar('*');
        }//小trick
        this.add(username);
        this.add(password);


        Point buttonLocation = new Point(center.x, center.y - 100);
        submitBtn = FrameUtil.createButton(this, "Confirm", new Point((int) (buttonLocation.getX() - width / 2.7), (int) buttonLocation.getY()), 150, 50);
        super.setButtonBackground(submitBtn, loginPath, loginRolloverPath, loginPressedPath);
        registerBtn = FrameUtil.createButton(this, "Register", new Point((int) (buttonLocation.getX() + width / 2.5 - submitBtn.getWidth()), (int) buttonLocation.getY()), 150, 50);
        super.setButtonBackground(registerBtn, registerPath, registerRolloverPath, registerPressedPath);
        this.add(registerBtn);
        this.add(submitBtn);

        submitBtn.addActionListener(e -> {
            int condition = AccountManager.checkPassword(username.getText(), password.getPassword());

            if (condition == 0) {//检查用户名与密码
                if (username.getText() != null) {
                    getRlevel().getrGameState().setCurrentUser(username.getText());
                }//存入当前userID
                LoginLevel rlevel = (LoginLevel) super.getRlevel();
                rlevel.nextLevel();
            } else if (condition == -1) {
                this.dialog = new DefaultDialog(this,"Error!", true," Username or password is not valid!"," ");
                System.out.println("Username or password is not valid!");
            } else if (condition == 1) {
                this.dialog = new DefaultDialog(this,"Error!", true,"Have not registered yet or data missed "," ");
                System.out.println("Have not registered yet or data missed");
            } else if (condition == 2) {
                this.dialog = new DefaultDialog(this,"Error!", true,"Wrong username or password! "," ");
                System.out.println("Wrong username or password!");
            } else {
                this.dialog = new DefaultDialog(this,"Error!", true,"Unknown error! "," ");
                System.out.println("Unknown Error!");
            }
        });
        registerBtn.addActionListener(e -> {
            int condition = AccountManager.registerAccount(username.getText(), password.getPassword());
            switch (condition) {
                case -1:
                    this.dialog = new DefaultDialog(this,"Error!", true," Username or password is not valid!"," ");
                    break;
                case 0:
                    this.dialog = new DefaultDialog(this,"Success!", true," Registered Successfully!"," ");
                    break;
                default:
                    this.dialog = new DefaultDialog(this,"Error!", true,"Unknown error! "," ");
                    break;
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        super.setBackground(imgPath);
    }


    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getResetBtn() {
        return registerBtn;
    }

}
