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
    private final String startRolloverPath = "./img/button/start_rollover.png";
    private final String startPressedPath = "./img/button/start_pressed.png";

    private final String settingPath = "./img/button/settings.png";
    private final String settingRolloverPath = "./img/button/settings_rollover.png";
    private final String settingPressedPath = "./img/button/settings_pressed.png";
    private final String loadPath = "./img/button/load.png";
    private final String loadRolloverPath = "./img/button/load_rollover.png";
    private final String loadPressedPath = "./img/button/load_pressed.png";
    private final String exitPath = "./img/button/exit.png";
    private final String exitRolloverPath = "./img/button/exit_rollover.png";
    private final String exitPressedPath = "./img/button/exit_pressed.png";


    private final String imgPath = "./img/selectFrame.png";
    private final String img2Path = "./img/settingFrame.png";

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
        super.setButtonBackground(startGameBtn,startPath,startRolloverPath,startPressedPath);

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
            super.setButtonBackground(loadGameBtn,loadPath,loadRolloverPath,loadPressedPath);
        }

        //setting button
        JButton settingsBtn = new JButton("Settings");
        btnPanel.add(settingsBtn);
        settingsBtn.addActionListener(e -> {
            new SettingFrame(level, title, width, height,img2Path);
        });

        super.setButtonBackground(settingsBtn, settingPath,settingRolloverPath,settingPressedPath);

        //exit button
        JButton exitGameBtn = new JButton("Exit Game");
        exitGameBtn.setVisible(true);
        exitGameBtn.setEnabled(true);
        btnPanel.add(exitGameBtn);
        exitGameBtn.addActionListener(e -> {
            System.exit(0);
        });
        super.setButtonBackground(exitGameBtn, exitPath,exitRolloverPath,exitPressedPath);




        super.setBackground(imgPath);
    }
}
