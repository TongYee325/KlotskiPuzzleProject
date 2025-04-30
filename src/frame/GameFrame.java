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
        setPanels(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void setPanels(int width, int height) {
        //main panel是主要面板，包含了game panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        gamePanel = new GamePanel(rMap,this);
        mainPanel.add(gamePanel);
        //tools panel 包含save和restart按钮，用来存档和重启游戏
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(2, 1));
        toolsPanel.setBackground(Color.WHITE);
        mainPanel.add(toolsPanel);
        toolsPanel.setBounds(width *55/100, height *10/100, width /3, height /3);
        JButton saveButton = new JButton("Save");
        saveButton.setVisible(true);
        toolsPanel.add(saveButton);
        saveButton.addActionListener(e -> {
            rlevel.getrGameState().saveGameData();
        });
        JButton restartButton = new JButton("Restart");
        toolsPanel.add(restartButton);
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
        //move button panel包含四个按钮，上下左右
        JPanel movePanel = new JPanel();
        movePanel.setLayout(null);
        mainPanel.add(movePanel);
        movePanel.setBounds(20, height *60/100, 300*80/100, 200*80/100);
        movePanel.setLayout(new GridLayout(2, 3));
        JButton nothingButton = new JButton("/");
        JButton nothingButton2 = new JButton("/");
        nothingButton.setVisible(false);
        nothingButton2.setVisible(false);
        movePanel.add(nothingButton);
        JButton upbtn = new JButton("Up");
        movePanel.add(upbtn);
        movePanel.add(nothingButton2);
        JButton downbtn = new JButton("Down");
        JButton leftbtn = new JButton("Left");
        JButton rightbtn = new JButton("Right");
        upbtn.addActionListener(e -> gamePanel.doMoveUp());
        downbtn.addActionListener(e -> gamePanel.doMoveDown());
        leftbtn.addActionListener(e -> gamePanel.doMoveLeft());
        rightbtn.addActionListener(e -> gamePanel.doMoveRight());
        movePanel.add(leftbtn);
        movePanel.add(downbtn);
        movePanel.add(rightbtn);
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




