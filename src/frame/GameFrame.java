package frame;


import gamemode.AiGameMode;
import level.GameLevel;
import level.map.GameMap;
import level.LevelBase;
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


    private final String upPath="./img/button/up.png";
    private final String upRolloverPath="./img/button/up_rollover.png";
    private final String upPressedPath="./img/button/up_pressed.png";
    private final String downPath="./img/button/down.png";
    private final String downRolloverPath="./img/button/down_rollover.png";
    private final String downPressedPath="./img/button/down_pressed.png";
    private final String leftPath="./img/button/left.png";
    private final String leftRolloverPath="./img/button/left_rollover.png";
    private final String leftPressedPath="./img/button/left_pressed.png";
    private final String rightPath="./img/button/right.png";
    private final String rightRolloverPath="./img/button/right_rollover.png";
    private final String rightPressedPath="./img/button/right_pressed.png";
    private final String savePath = "./img/tool/tsave.png";
    private final String saveRolloverPath = "./img/tool/tsave_rollover.png";
    private final String savePressedPath = "./img/tool/tsave_pressed.png";
    private final String backPath = "./img/tool/tback.png";
    private final String backRolloverPath = "./img/tool/tback_rollover.png";
    private final String backPressedPath = "./img/tool/tback_pressed.png";
    private final String restartPath = "./img/tool/trestart.png";
    private final String restartRolloverPath = "./img/tool/trestart_rollover.png";
    private final String restartPressedPath = "./img/tool/trestart_pressed.png";
    private final String revokePath = "./img/tool/trevoke.png";
    private final String revokeRolloverPath = "./img/tool/trevoke_rollover.png";
    private final String revokePressedPath = "./img/tool/trevoke_pressed.png";
    private final String aiMovePath = "./img/tool/taiMove.png";
    private final String aiMoveRolloverPath = "./img/tool/taiMove_rollover.png";
    private final String aiMovePressedPath = "./img/tool/taiMove_pressed.png";





    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap,String imgPath) {
        super(level, title, width, height);
        rlevel = level;
        // 从游戏状态获取模式选择
        isTimedMode = ((GameLevel) rlevel).isTimeMode();



        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        rMap = gameMap;
        initialComponents(width, height);
        setLocationRelativeTo(null);
        setVisible(true);




        super.setBackground(imgPath);
    }

    private void initialComponents(int width, int height) {
        // 主面板配置（保持原有代码不变）
        mainPanel = new JPanel();
        gamePanel = new GamePanel(rMap, this);
        toolsPanel = new JPanel();
        JButton saveButton = new JButton();
        saveButton.setVisible(false);
        saveButton.setEnabled(false);
        JButton restartButton = new JButton();
        JButton revokeButton = new JButton();
        JButton backButton = new JButton();
        JButton aiMoveButton = new JButton();
        movePanel = new JPanel();
        JButton downbtn = new JButton();
        JButton leftbtn = new JButton();
        JButton rightbtn = new JButton();
        JButton upbtn = new JButton();
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
        toolsPanel.setOpaque(false);
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
            super.setButtonBackground(saveButton,savePath,saveRolloverPath,savePressedPath);
        }

        // restart button配置（保持原有代码不变）
        toolsPanel.add(restartButton);
        restartButton.addActionListener(e -> {
            rlevel.getrGameState().getMyLogSystem().printAllSteps();
            rlevel.getrGameState().getMyLogSystem().clearSteps();
            ((AiGameMode) ((GameLevel) rlevel).getrGameModeBase()).stopExecutingSteps();
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
            if(rlevel.getrGameState().getCurrentUserId() != null) {
                saveButton.setEnabled(true);
            }
        });
        super.setButtonBackground(restartButton,restartPath,restartRolloverPath,restartPressedPath);

        // revoke button配置（保持原有代码不变）
        toolsPanel.add(revokeButton);
        revokeButton.addActionListener(e -> {
            gamePanel.revoke();
        });
        super.setButtonBackground(revokeButton,revokePath,revokeRolloverPath,revokePressedPath);

        // back button配置（保持原有代码不变）
        toolsPanel.add(backButton);
        backButton.addActionListener(e -> {
            ((AiGameMode) ((GameLevel) rlevel).getrGameModeBase()).stopExecutingSteps();
            rlevel.getrGameState().startLevel(1);
        });
        super.setButtonBackground(backButton,backPath,backRolloverPath,backPressedPath);

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
        super.setButtonBackground(aiMoveButton,aiMovePath,aiMoveRolloverPath,aiMovePressedPath);

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



        super.setButtonBackground(upbtn,upPath,upRolloverPath,upPressedPath);

        upbtn.addActionListener(e -> gamePanel.doMoveUp(gamePanel.getSelectedBlock(), true));
        downbtn.addActionListener(e -> gamePanel.doMoveDown(gamePanel.getSelectedBlock(), true));
        leftbtn.addActionListener(e -> gamePanel.doMoveLeft(gamePanel.getSelectedBlock(), true));
        rightbtn.addActionListener(e -> gamePanel.doMoveRight(gamePanel.getSelectedBlock(), true));
        movePanel.add(leftbtn);
        movePanel.add(downbtn);
        movePanel.add(rightbtn);

        super.setButtonBackground(leftbtn,leftPath,leftRolloverPath,leftPressedPath);
        super.setButtonBackground(rightbtn,rightPath,rightRolloverPath,rightPressedPath);
        super.setButtonBackground(downbtn,downPath,downRolloverPath,downPressedPath);
        movePanel.setOpaque(false);



        // info panel配置
        mainPanel.add(infoPanel);
        infoPanel.setLayout(new GridLayout(3, 1));
        infoPanel.setBounds(width *55/100, height *60/100, 250, height /5);
        this.stepLabel = stepsLabel;
        stepsLabel.setFont(new Font("Algerian", Font.BOLD, 25));
        stepsLabel.setForeground(Color.darkGray);


        infoPanel.add(stepsLabel);
        timeLabel = new JLabel(formatTime(elapsedTime));
        timeLabel.setFont(new Font("Algerian", Font.BOLD, 25));
        timeLabel.setForeground(Color.darkGray);
        infoPanel.add(timeLabel);

        infoPanel.setOpaque(false);



        timeInfoPanel.setLayout(new BorderLayout()); // 增加一行用于模式选择
        timeInfoPanel.setOpaque(false);
        // 剩余时间标签
        remainingTimeLabel = new JLabel("RemainTime : --:--");
        remainingTimeLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        /*remainingTimeLabel.setMinimumSize(new Dimension(120, 25));
        remainingTimeLabel.setPreferredSize(new Dimension(120, 25));*/


        remainingTimeLabel.setOpaque(false);
        timeInfoPanel.add(remainingTimeLabel,BorderLayout.CENTER);

        //是限时模式才添加剩余时间UI
        if(isTimedMode){
            infoPanel.add(timeInfoPanel);
            timeInfoPanel.setBounds(0,0,250,50);
        }


        // 保存提示标签（保持原有代码不变）
        saveTipLabel = new JLabel("Game has been saved!");
        saveTipLabel.setVisible(false);

        saveTipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveTipLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        saveTipLabel.setForeground(Color.GREEN);
        mainPanel.add(saveTipLabel);
        saveTipLabel.setBounds(330,300,250,50);

        mainPanel.setOpaque(false);
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
            updateRemainTimer(gameLevel);
        });

        countdownTimer.start();
    }

    public void updateRemainTimer(GameLevel gameLevel) {
        long remaining = gameLevel.getRemainTime() - 1000;
        gameLevel.setRemainTime(remaining);

        // 在事件调度线程上更新UI
        updateRemainingTimeUI(remaining);

        if (remaining <= 0) {
            countdownTimer.stop();
            gameOver();
        }
    }

    // 辅助方法：更新剩余时间UI
    private void updateRemainingTimeUI(long remainingMs) {
        SwingUtilities.invokeLater(() -> {
            long totalMs = Math.max(remainingMs, 0);
            long minutes = totalMs / (1000 * 60);
            long seconds = (totalMs % (1000 * 60)) / 1000;

            remainingTimeLabel.setText("Remain time : " + String.format("%02d:%02d", minutes, seconds));

            // 根据剩余时间改变颜色
            if (remainingMs <= 60000) {
                remainingTimeLabel.setForeground(Color.RED);
            } else {
                remainingTimeLabel.setForeground(Color.darkGray);
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
            stopRemainTimer();
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
        if (gameTimer != null&&gameTimer.isRunning()) {
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
        stopRemainTimer();
        gameTimer = new Timer(1000, e -> updateTimer());
        gameTimer.start();
        startRemainTimer();
    }

    //计时部分^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public void frameDestroyed() {
        stopTimer();//停止计时
        stopRemainTimer();
        this.removeAll();
    }

    public void stopRemainTimer() {
        if(countdownTimer!=null){
            countdownTimer.stop();
        }
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
        gamePanel.updateStepsAndTotalTime();
        showTimeOutInfo();

    }

    private void showTimeOutInfo() {
/*        SwingUtilities.invokeLater(() -> {
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
        });*/

        SwingUtilities.invokeLater(() -> {
            // 构建胜利对话框的中间内容面板（统计信息）
            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));

            // 总步数面板
            JPanel stepsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel stepsLabel = new JLabel("Total Steps:");
            stepsLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            JTextField stepsField = new JTextField(String.valueOf(gamePanel.getTotalSteps()), 15);
            stepsField.setEditable(false);
            stepsField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            stepsField.setBackground(Color.WHITE);
            stepsField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            stepsPanel.add(stepsLabel);
            stepsPanel.add(stepsField);

            // 总时间面板
            JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel timeLabel = new JLabel("Total Time:");
            timeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            JTextField timeField = new JTextField(formatTime(gamePanel.getTotalTime()), 15);
            timeField.setEditable(false);
            timeField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            timeField.setBackground(Color.WHITE);
            timeField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            timePanel.add(timeLabel);
            timePanel.add(timeField);



            contentPanel.add(stepsPanel, BorderLayout.NORTH);
            contentPanel.add(timePanel, BorderLayout.CENTER);

            // 调用 DefaultDialog
            new DefaultDialog(
                    this,
                    "Game Over!",
                    true,
                    "Time Out!",
                    contentPanel,
                    "OK",
                    new Dimension(450, 250)
            );
        });
    }


}