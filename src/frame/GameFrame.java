package frame;

import frame.block.*;
import level.GameMap;
import level.LevelBase;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GameFrame extends FrameBase {

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    private GamePanel gamePanel;
    private GameMap map;

    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap) {
        super(level, title, width, height);
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        map = gameMap;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        gamePanel = new GamePanel(map);
        mainPanel.add(gamePanel);


        JButton restartButton = new JButton("Restart");
        this.add(restartButton, BorderLayout.SOUTH);
        restartButton.addActionListener(e -> {
            gamePanel = new GamePanel(map);
            mainPanel.add(gamePanel);
        });
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void initialGame(){
        gamePanel.initialGame();

    }

}




