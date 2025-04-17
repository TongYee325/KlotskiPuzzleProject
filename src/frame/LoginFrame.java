package frame;



import level.AccountManager;
import level.LoginLevel;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends FrameBase {
    private JTextField username;
    private JTextField password;


    private JButton submitBtn;
    private JButton registerBtn;
    private JPanel textPanel;

    public LoginFrame(LoginLevel loginLevel,String title, int width, int height) {
        super(loginLevel,title, width, height);
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point userLocation = new Point(center.x-width/4, center.y-height/3);
        Point passwordLocation = new Point(center.x-width/4, center.y-height/5);
        JLabel userLabel = FrameUtil.createJLabel(this,userLocation, 70, 40, "username:");
        JLabel passLabel = FrameUtil.createJLabel(this, passwordLocation, 70, 40, "password:");
        //todo:add hint test box

        username = FrameUtil.createJTextField(this, new Point((int) (userLocation.getX()+userLabel.getWidth()), (int) userLocation.getY()), 200, 40);
        password = FrameUtil.createJTextField(this, new Point((int) (passwordLocation.getX()+passLabel.getWidth()), (int) passwordLocation.getY()), 200, 40);

        Point buttonLocation = new Point(center.x, center.y);
        submitBtn = FrameUtil.createButton(this, "Confirm", new Point((int) (buttonLocation.getX()-width/4.0), (int) buttonLocation.getY()), 100, 40);
        registerBtn = FrameUtil.createButton(this, "Register", new Point((int) (buttonLocation.getX()+width/4.0-submitBtn.getWidth()), (int) buttonLocation.getY()), 100, 40);

        submitBtn.addActionListener(e -> {

            if(AccountManager.checkPassword(username.getText(), password.getText())){//检查用户名与密码
                LoginLevel rlevel = (LoginLevel)  super.getRlevel();
                rlevel.nextLevel();
            }else {
                System.out.println("Wrong Username or Password");
            }


        });
        registerBtn.addActionListener(e -> {
            System.out.println(AccountManager.registerAccount(username.getText(), password.getText()));//注册


        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void update(){
        submitBtn.setVisible(true);
        registerBtn.setVisible(true);
    }


    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getResetBtn() {
        return registerBtn;
    }

}
