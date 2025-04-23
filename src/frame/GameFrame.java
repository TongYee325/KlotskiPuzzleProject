package frame;

import controller.MyGameController;
import frame.block.*;
import level.GameLevel;
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
    private LevelBase rlevel;

    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap) {
        super(level, title, width, height);
        rlevel = level;

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
            if(gamePanel!=null){
                mainPanel.remove(gamePanel);
                gamePanel=null;
            }
            gamePanel = new GamePanel(map);
            mainPanel.add(gamePanel);
            ((GameLevel) rlevel).getController().updateFrame();
            gamePanel.requestFocusInWindow();
            gamePanel.initialGame();
        });
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void initialGame() {
        gamePanel.initialGame();

    }

}




