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

        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(2, 1));
        toolsPanel.setBackground(Color.WHITE);
        mainPanel.add(toolsPanel);
        toolsPanel.setBounds(width*55/100,height*10/100,width/3,height/3);
        JButton saveButton = new JButton("Save");
        saveButton.setVisible(true);
        saveButton.addActionListener(e -> {
            rlevel.getrGameState().getMySaveManager().saveGame();
        });
        toolsPanel.add(saveButton);


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

    public void loadGame(int [][] panelMap) {
        gamePanel.initialGame(panelMap);
    }



    public GamePanel getGamePanel() {
        return gamePanel;
    }


}




