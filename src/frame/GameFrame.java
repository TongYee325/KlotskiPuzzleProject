package frame;

import level.GameLevel;
import level.GameMap;
import level.LevelBase;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends FrameBase {
    private GamePanel gamePanel;
    private GameMap rMap;
    private LevelBase rlevel;

    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap) {
        super(level, title, width, height);
        rlevel = level;

        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        rMap = gameMap;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        gamePanel = new GamePanel(rMap,this);
        mainPanel.add(gamePanel);


        JButton restartButton = new JButton("Restart");
        this.add(restartButton, BorderLayout.SOUTH);
        restartButton.addActionListener(e -> {
            //print and clear log
            rlevel.getrGameState().getMyLogSystem().printAllSteps();
            rlevel.getrGameState().getMyLogSystem().clearSteps();
            if(gamePanel!=null){
                mainPanel.remove(gamePanel);
                gamePanel=null;
            }
            gamePanel = new GamePanel(rMap,this);
            mainPanel.add(gamePanel);
            ((GameLevel) rlevel).getGameController().updateControlledPanelAccordingToLevel();
            gamePanel.initialGame();
        });
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void initialGame() {
        gamePanel.initialGame();

    }




    public GamePanel getGamePanel() {
        return gamePanel;
    }

}




