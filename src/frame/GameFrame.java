package frame;


import gamemode.AiGameMode;
import level.GameLevel;
import level.map.GameMap;
import level.LevelBase;
import gamestate.MyGameState;
import frame.dialog.DefaultDialog;

import javax.swing.*;
import java.awt.*;

import static gamestate.MyGameState.TIME_LIMIT;

public class GameFrame extends FrameBase {

    // 面板数据
    private GamePanel gamePanel;
    private JPanel mainPanel;
    private JPanel toolsPanel;
    private JPanel movePanel;
    private JPanel infoPanel;
    // 游戏初始地图
    private GameMap rMap;
    // 关卡指针
    private LevelBase rlevel;
    // 步数显示
    private JLabel stepLabel;
    // 计时
    private long startTime;
    private long elapsedTime;
    private Timer gameTimer;
    private JLabel timeLabel;
    // 存档信息显示
    private JLabel saveTipLabel;
    private Timer tipTimer;

    private JLabel remainingTimeLabel;
    private Timer countdownTimer;
    private boolean isTimedMode = false;


    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap) {
        super(level, title, width, height);
        rlevel = level;
        // 从游戏状态获取模式选择
        isTimedMode = ((GameLevel) rlevel).isTimeMode();



        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        rMap = gameMap;
        setPanels(width, height);
        setLocationRelativeTo(null);
        setVisible(true);





    }

    private void setPanels(int width, int height) {
        // 主面板配置（保持原有代码不变）
        mainPanel = new JPanel();
        gamePanel = new GamePanel(rMap, this);
        toolsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.setVisible(false);
        saveButton.setEnabled(false);
        JButton restartButton = new JButton("Restart");
        JButton revokeButton = new JButton("Revoke");
        JButton backButton = new JButton("Back");
        JButton aiMoveButton = new JButton("AI Move");
        movePanel = new JPanel();
        JButton downbtn = new JButton("Down");
        JButton leftbtn = new JButton("Left");
        JButton rightbtn = new JButton("Right");
        JButton upbtn = new JButton("Up");
        JButton nothingButton = new JButton("/");
        JButton nothingButton2 = new JButton("/");
        infoPanel = new JPanel();
        JPanel timeInfoPanel = new JPanel();
        JLabel stepsLabel = new JLabel("Steps : 0 ");

        // main panel配置（保持原有代码不变）
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(gamePanel);

        // tools panel配置（保持原有代码不变）
        toolsPanel.setLayout(new GridLayout(5, 1));
        if(rlevel.getrGameState().getCurrentUserId()==null) {
            toolsPanel.setLayout(new GridLayout(4, 1));
        }
        toolsPanel.setBackground(Color.WHITE);
        mainPanel.add(toolsPanel);
        toolsPanel.setBounds(width *55/100, height *10/100, width /3, height /3);

        // save button配置（保持原有代码不变）
        if(rlevel.getrGameState().getCurrentUserId()!=null) {
            saveButton.setVisible(true);
            saveButton.setEnabled(true);
            toolsPanel.add(saveButton);
            saveButton.addActionListener(e -> {
                ((GameLevel) rlevel).saveGame();
            });
        }

        // restart button配置（保持原有代码不变）
        toolsPanel.add(restartButton);
        restartButton.addActionListener(e -> {
            rlevel.getrGameState().getMyLogSystem().printAllSteps();
            rlevel.getrGameState().getMyLogSystem().clearSteps();
            if(gamePanel!=null){
                mainPanel.remove(gamePanel);
                gamePanel=null;
            }
            gamePanel = new GamePanel(rMap, this);
            mainPanel.add(gamePanel);
            ((GameLevel) rlevel).getGameController().updateControlledPanelAccordingToLevel();
            initialGame();
            gameRestart();
            revokeButton.setEnabled(true);
            aiMoveButton.setEnabled(true);
        });

        // revoke button配置（保持原有代码不变）
        toolsPanel.add(revokeButton);
        revokeButton.addActionListener(e -> {
            gamePanel.revoke();
        });

        // back button配置（保持原有代码不变）
        toolsPanel.add(backButton);
        backButton.addActionListener(e -> {
            rlevel.getrGameState().startLevel(1);
        });

        // aiMove button配置（保持原有代码不变）
        toolsPanel.add(aiMoveButton);
        aiMoveButton.addActionListener(e -> {
            aiMoveButton.setEnabled(false);
            revokeButton.setEnabled(false);
            if(rlevel.getrGameState().getCurrentUserId() != null) {
                saveButton.setEnabled(false);
            }
            ((AiGameMode) ((GameLevel) rlevel).getrGameModeBase()).aiMove(getGamePanel().getPanelMap());
        });

        // move button panel配置
        movePanel.setLayout(null);
        mainPanel.add(movePanel);
        movePanel.setBounds(20, height *60/100, 300*80/100, 200*80/100);
        movePanel.setLayout(new GridLayout(2, 3));

        nothingButton.setVisible(false);
        nothingButton2.setVisible(false);
        movePanel.add(nothingButton);
        movePanel.add(upbtn);
        movePanel.add(nothingButton2);

        upbtn.addActionListener(e -> gamePanel.doMoveUp(gamePanel.getSelectedBlock(), true));
        downbtn.addActionListener(e -> gamePanel.doMoveDown(gamePanel.getSelectedBlock(), true));
        leftbtn.addActionListener(e -> gamePanel.doMoveLeft(gamePanel.getSelectedBlock(), true));
        rightbtn.addActionListener(e -> gamePanel.doMoveRight(gamePanel.getSelectedBlock(), true));
        movePanel.add(leftbtn);
        movePanel.add(downbtn);
        movePanel.add(rightbtn);

        // info panel配置
        mainPanel.add(infoPanel);
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.setBounds(width *55/100, height *60/100, width /3, height /5);
        this.stepLabel = stepsLabel;
        infoPanel.add(stepsLabel);
        timeLabel = new JLabel(formatTime(elapsedTime));
        infoPanel.add(timeLabel);


        timeInfoPanel.setLayout(new BorderLayout()); // 增加一行用于模式选择
        // 剩余时间标签
        remainingTimeLabel = new JLabel("Remaining Time : --:--");
        remainingTimeLabel.setForeground(new Color(0, 100, 0));
        remainingTimeLabel.setMinimumSize(new Dimension(120, 25));
        remainingTimeLabel.setPreferredSize(new Dimension(120, 25));
        timeInfoPanel.add(remainingTimeLabel,BorderLayout.CENTER);

        //是限时模式才添加剩余时间UI
        if(isTimedMode){
            mainPanel.add(timeInfoPanel);
            timeInfoPanel.setBounds(width *55/100,485,250,50);
        }


        // 保存提示标签（保持原有代码不变）
        saveTipLabel = new JLabel("Game has been saved!");
        saveTipLabel.setVisible(false);
        saveTipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(saveTipLabel);
    }

    public void showSaveInfo() {
        saveTipLabel.setVisible(true);

        if(tipTimer != null && tipTimer.isRunning()) {
            tipTimer.stop();
        }

        tipTimer = new Timer(3000, e -> {
            saveTipLabel.setVisible(false);
            repaint();
        });
        tipTimer.setRepeats(false);
        tipTimer.start();
    }
    // 新增方法：启动倒计时
    private void startCountdown() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        GameLevel gameLevel = (GameLevel) rlevel;
        // 确保初始时间正确设置
        if (gameLevel.getRemainTime() <= 0) {
            gameLevel.setRemainTime(TIME_LIMIT);
        }

        // 更新UI显示初始时间
        updateRemainingTimeUI(gameLevel.getRemainTime());

        countdownTimer = new Timer(1000, e -> {
            long remaining = gameLevel.getRemainTime() - 1000;
            gameLevel.setRemainTime(remaining);

            // 在事件调度线程上更新UI
            updateRemainingTimeUI(remaining);

            if (remaining <= 0) {
                countdownTimer.stop();
                gameOver();
            }
        });

        countdownTimer.start();
    }

    // 辅助方法：更新剩余时间UI
    private void updateRemainingTimeUI(long remainingMs) {
        SwingUtilities.invokeLater(() -> {
            long totalMs = Math.max(remainingMs, 0);
            long minutes = totalMs / (1000 * 60);
            long seconds = (totalMs % (1000 * 60)) / 1000;

            remainingTimeLabel.setText("Remaining time : " + String.format("%02d:%02d", minutes, seconds));

            // 根据剩余时间改变颜色
            if (remainingMs <= 60000) {
                remainingTimeLabel.setForeground(Color.RED);
            } else {
                remainingTimeLabel.setForeground(Color.BLACK);
            }

            // 确保UI刷新
            remainingTimeLabel.revalidate();
            remainingTimeLabel.repaint();
        });
    }

    public void initialGame() {
        gamePanel.initialGame();
        setTimer();

        startRemainTimer();
        updateStep();
        updateTimer();
    }

    private void startRemainTimer() {
        if (isTimedMode) {
            ((GameLevel) rlevel).setRemainTime(TIME_LIMIT);
            remainingTimeLabel.setVisible(true);
            remainingTimeLabel.setText("Remaining time：--:--");
            remainingTimeLabel.setForeground(Color.BLACK);
            startCountdown();
        } else {
            remainingTimeLabel.setVisible(false);
            if (countdownTimer != null) {
                countdownTimer.stop();
            }
        }
    }

    public void initialGame(int [][] panelMap) {
        gamePanel.initialGame(panelMap);
        loadGameTimer();
        setTimer();
        startRemainTimer();
        updateStep();
        updateTimer();
    }

    private void gameRestart() {
        timerRestart();
        updateStep();

        // 如果是限时模式，重置剩余时间
        if (isTimedMode) {
            ((GameLevel) rlevel).setRemainTime(TIME_LIMIT);
            updateRemainingTimeUI(TIME_LIMIT);
        }
    }


    //更新步数
    public void updateStep(){
        gamePanel.afterMove();
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
        if(countdownTimer!=null){
            countdownTimer.stop();
        }
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


    //游戏结束处理
    private void gameOver() {
        if (gameTimer != null) gameTimer.stop();
        if (countdownTimer != null) countdownTimer.stop();
        SwingUtilities.invokeLater(() -> {
            // 调用 DefaultDialog
            new DefaultDialog(
                    this,
                    "Game Over",
                    true,
                    "Time out!",
                    null,
                    "OK",
                    new Dimension(350, 200)
            );
        });

    }




}