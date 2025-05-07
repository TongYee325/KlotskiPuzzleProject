package frame;

import level.GameLevel;
import level.map.GameMap;
import level.LevelBase;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends FrameBase {

    //面板数据
    private GamePanel gamePanel;
    private JPanel mainPanel;
    private JPanel toolsPanel;
    private JPanel movePanel;
    private JPanel infoPanel;
    //游戏初始地图
    private GameMap rMap;
    //关卡指针
    private LevelBase rlevel;
    //步数显示
    private JLabel stepLabel;
    //计时
    private long startTime;
    private long elapsedTime;
    private Timer gameTimer;
    private JLabel timeLabel;
    //存档信息显示
    private JLabel saveTipLabel;
    private Timer tipTimer;

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

    private void setPanels(int width, int height) {//面板配置
        //main panel是主要面板，包含了game panel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        gamePanel = new GamePanel(rMap,this);
        mainPanel.add(gamePanel);
        //tools panel 包含save、restart、revoke按钮，用来存档和重启游戏和撤销
        toolsPanel = new JPanel();
        toolsPanel.setLayout(new GridLayout(4, 1));
        if(rlevel.getrGameState().getCurrentUserId()==null) {toolsPanel.setLayout(new GridLayout(3, 1));}
        toolsPanel.setBackground(Color.WHITE);
        mainPanel.add(toolsPanel);
        toolsPanel.setBounds(width *55/100, height *10/100, width /3, height /3);
            //save button
        if(rlevel.getrGameState().getCurrentUserId()!=null) {
            JButton saveButton = new JButton("Save");
            saveButton.setVisible(true);
            toolsPanel.add(saveButton);
            saveButton.addActionListener(e -> {
                ((GameLevel) rlevel).saveGame();
            });
        }
            //restart button
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
            initialGame();
            gameRestart();
        });
            //revoke button
        JButton revokeButton = new JButton("Revoke");
        toolsPanel.add(revokeButton);
        revokeButton.addActionListener(e -> {
            gamePanel.revoke();
        });
            //back button
        JButton backButton = new JButton("Back");
        toolsPanel.add(backButton);
        backButton.addActionListener(e -> {
            rlevel.getrGameState().startLevel(1);//重回menuLevel
        });
        //move button panel包含四个按钮，上下左右
        movePanel = new JPanel();
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
        upbtn.addActionListener(e -> gamePanel.doMoveUp(gamePanel.getSelectedBlock(),true));
        downbtn.addActionListener(e -> gamePanel.doMoveDown(gamePanel.getSelectedBlock(),true));
        leftbtn.addActionListener(e -> gamePanel.doMoveLeft(gamePanel.getSelectedBlock(),true));
        rightbtn.addActionListener(e -> gamePanel.doMoveRight(gamePanel.getSelectedBlock(),true));
        movePanel.add(leftbtn);
        movePanel.add(downbtn);
        movePanel.add(rightbtn);
        //info panel 记录游戏开始时间和总共走的步数
        infoPanel = new JPanel();
        mainPanel.add(infoPanel);
        infoPanel.setLayout(new GridLayout(3, 1));
        JLabel stepsLabel = new JLabel("Steps : 0 ");
        this.stepLabel = stepsLabel;
        infoPanel.add(stepsLabel);
        timeLabel = new JLabel(formatTime(elapsedTime));
        infoPanel.add(timeLabel);
        infoPanel.setBounds(width *55/100, height *60/100, width /3, height /5);
        updateStep();
        saveTipLabel = new JLabel("Game has been saved!");
        saveTipLabel.setVisible(false);
        infoPanel.add(saveTipLabel);
    }


    public void showSaveInfo(){
        saveTipLabel.setVisible(true);

        // 如果已有计时器先停止
        if(tipTimer != null && tipTimer.isRunning()) {
            tipTimer.stop();
        }

        // 创建新的计时器
        tipTimer = new Timer(3000, e -> {
            saveTipLabel.setVisible(false);
            repaint();
        });
        tipTimer.setRepeats(false); // 只执行一次
        tipTimer.start();
    }


    public void initialGame() {
        gamePanel.initialGame();
        setTimer();//开始计时
    }

    public void initialGame(int [][] panelMap) {
        gamePanel.initialGame(panelMap);
        loadGameTimer();
        setTimer();
    }

    private void gameRestart() {
        timerRestart();
        updateStep();
    }


    //更新步数
    public void updateStep(){
        stepLabel.setText(String.format("Steps : %d",rlevel.getrGameState().getMyLogSystem().getTotalSteps().size()));
    }

    //计时部分-----------------------------------------------------------------------------------------------------------

    private void setTimer(){
        //设置开始计时，以ms为单位
        startTime = System.currentTimeMillis()-elapsedTime;//如果有存档的话，这么做的目的是统一时间
        gameTimer = new Timer(1000, e -> updateTimer());
        gameTimer.start();
    }

    private void updateTimer(){
        if (startTime > 0) {
            elapsedTime = System.currentTimeMillis() - startTime;
            SwingUtilities.invokeLater(() -> {
                timeLabel.setText(formatTime(elapsedTime));
            });
        }
    }

    private String formatTime(long elapsedTime) {
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60));
        long hours = (elapsedTime / (1000 * 60 * 60));
        if(minutes ==0&&hours==0){return String.format("Time : %02d ", seconds);}
        if(hours==0){return String.format("Time : %02d:%02d ", minutes, seconds);}
        return String.format("Time : %02d:%02d:%02d ", hours, minutes, seconds);
    }

    void stopTimer(){
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    private void loadGameTimer() {
        this.elapsedTime=rlevel.getrGameState().getMyLogSystem().getElapsedTime();
        timeLabel.setText(formatTime(elapsedTime));
    }

    private void timerRestart() {
        timeLabel.setText(formatTime(0));
        elapsedTime = 0;
        startTime = System.currentTimeMillis();
        stopTimer();
        gameTimer = new Timer(1000, e -> updateTimer());
        gameTimer.start();
    }

    //计时部分^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void frameDestroyed() {
        stopTimer();//停止计时
        this.removeAll();
    }







    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }



}




