package frame;


import level.LevelBase;
import level.MenuLevel;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends FrameBase {

    private JButton startGameBtn;
    private JButton loadGameBtn;
    private MenuLevel rLevel;

    public MenuFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.rLevel = (MenuLevel) level;
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.white);
        btnPanel.setVisible(true);
        this.add(btnPanel);
        btnPanel.setBounds(center.x-300/2, center.y-300/2, 300, 200);
        btnPanel.setLayout(new GridLayout(3,1));//游客模式下三个按钮
        //start button
        startGameBtn = new JButton("Start Game");
        btnPanel.add(startGameBtn);
        startGameBtn.addActionListener(e -> {
            rLevel.switchToSelectFrame();

        });

        if(rLevel.getrGameState().getCurrentUserId()!=null){//非游客模式下四个按钮,新增load game按钮
            btnPanel.setLayout(new GridLayout(4,1));
            loadGameBtn = new JButton("Load Game");
            loadGameBtn.setVisible(true);
            loadGameBtn.setEnabled(true);
            btnPanel.add(loadGameBtn);
            btnPanel.setBounds(center.x-300/2, center.y-300/2, 300, 300);
            loadGameBtn.addActionListener(e -> {
                getRlevel().getrGameState().loadGameData();
            });
        }

        //setting button
        JButton settingsBtn = new JButton("Settings");
        settingsBtn.setVisible(true);
        settingsBtn.setEnabled(true);
        btnPanel.add(settingsBtn);
        settingsBtn.addActionListener(e -> {
            //todo open setting frame
        });

        //exit button
        JButton exitGameBtn = new JButton("Exit Game");
        exitGameBtn.setVisible(true);
        exitGameBtn.setEnabled(true);
        btnPanel.add(exitGameBtn);
        exitGameBtn.addActionListener(e -> {
            System.exit(0);
        });




    }
}
