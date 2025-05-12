package frame;


import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends FrameBase {

    private JButton startGameBtn;
    private JButton loadGameBtn;
    private MenuLevel rLevel;

    private final String startPath = "./img/button/start.png";
    private final String settingPath = "./img/button/settings.png";
    private final String loadPath = "./img/button/load.png";
    private final String exitPath = "./img/button/exit.png";

    public MenuFrame(LevelBase level, String title, int width, int height,String imgPath) {
        super(level, title, width, height);
        this.rLevel = (MenuLevel) level;
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setVisible(true);
        this.add(btnPanel);
        btnPanel.setBounds(center.x-200/2, center.y+100/2, 200, 140);
        btnPanel.setLayout(new GridLayout(3,1));//游客模式下三个按钮
        //start button
        startGameBtn = new JButton("Start Game");
        btnPanel.add(startGameBtn);
        startGameBtn.addActionListener(e -> {
            rLevel.switchToSelectFrame();
        });
        super.setButtonBackground(startGameBtn,startPath);

        if(rLevel.getrGameState().getCurrentUserId()!=null){//非游客模式下四个按钮,新增load game按钮
            btnPanel.setLayout(new GridLayout(4,1));
            loadGameBtn = new JButton("Load Game");
            loadGameBtn.setVisible(true);
            loadGameBtn.setEnabled(true);
            btnPanel.add(loadGameBtn);
            btnPanel.setBounds(center.x-200/2, center.y+100/2, 200, 186);
            loadGameBtn.addActionListener(e -> {
                getRlevel().getrGameState().loadGameData();
                getRlevel().levelDestroy();
            });
            super.setButtonBackground(loadGameBtn,loadPath,loadPath,loadPath);
        }

        //setting button
        JButton settingsBtn = new JButton("Settings");
        btnPanel.add(settingsBtn);
        settingsBtn.addActionListener(e -> {
            new SettingFrame(level, title, width, height);
        });

        super.setButtonBackground(settingsBtn, settingPath,settingPath,settingPath);

        //exit button
        JButton exitGameBtn = new JButton("Exit Game");
        exitGameBtn.setVisible(true);
        exitGameBtn.setEnabled(true);
        btnPanel.add(exitGameBtn);
        exitGameBtn.addActionListener(e -> {
            System.exit(0);
        });
        super.setButtonBackground(exitGameBtn, exitPath,exitPath,exitPath);




        super.setBackground(imgPath);
    }
}
