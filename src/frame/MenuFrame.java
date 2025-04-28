package frame;


import level.LevelBase;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends FrameBase {

    private JButton startGameBtn;
    private JButton loadGameBtn;

    public MenuFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(2, 1));
        startGameBtn = new JButton("Start Game");
        loadGameBtn = new JButton("Load Game");
        btnPanel.add(startGameBtn);
        btnPanel.add(loadGameBtn);
        btnPanel.setBackground(Color.black);
        btnPanel.setVisible(true);
        this.add(btnPanel);
        btnPanel.setBounds(center.x-300/2, center.y-300/2, 300, 300);
        startGameBtn.addActionListener(e -> {
            level.nextLevel();
        });
        loadGameBtn.addActionListener(e -> {
            getRlevel().getrGameState().loadGameData();
            //todo load game logic
        });
    }
}
