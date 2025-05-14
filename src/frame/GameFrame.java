package frame;

import frame.audio.AudioManager;
import frame.dialog.VictoryDialog;
import gamemode.AiGameMode;
import level.GameLevel;
import level.map.GameMap;
import level.LevelBase;
import gamestate.MyGameState;
import frame.dialog.DefaultDialog;

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
    private static final long TIME_LIMIT = 3 * 60 * 1000; // 3分钟
    private JLabel remainingTimeLabel;
    private Timer countdownTimer;
    private boolean isTimedMode = false;


    public GameFrame(LevelBase level, String title, int width, int height, GameMap gameMap) {
        super(level, title, width, height);
        rlevel = level;
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        rMap = gameMap;
        setPanels(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        showModeSelection();
    }

    private void setPanels(int width, int height) {//面板配置
        mainPanel = new JPanel();
        gamePanel = new GamePanel(rMap,this);
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






        //main panel是主要面板，包含了game panel
        mainPanel.setLayout(null);
        this.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(gamePanel);
        //tools panel 包含save、restart、revoke、aiMove按钮，用来存档和重启游戏和撤销
        toolsPanel.setLayout(new GridLayout(5, 1));
        if(rlevel.getrGameState().getCurrentUserId()==null) {toolsPanel.setLayout(new GridLayout(4, 1));}
        toolsPanel.setBackground(Color.WHITE);
        mainPanel.add(toolsPanel);
        toolsPanel.setBounds(width *55/100, height *10/100, width /3, height /3);
            //save button
        if(rlevel.getrGameState().getCurrentUserId()!=null) {
            saveButton.setVisible(true);
            saveButton.setEnabled(true);
            toolsPanel.add(saveButton);
            saveButton.addActionListener(e -> {
                ((GameLevel) rlevel).saveGame();
            });
        }
            //restart button
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
            revokeButton.setEnabled(true);
            aiMoveButton.setEnabled(true);
        });
            //revoke button
        toolsPanel.add(revokeButton);
        revokeButton.addActionListener(e -> {
            gamePanel.revoke();
        });
            //back button
        toolsPanel.add(backButton);
        backButton.addActionListener(e -> {
            rlevel.getrGameState().startLevel(1);//重回menuLevel
        });
            //aiMove button
        toolsPanel.add(aiMoveButton);
        aiMoveButton.addActionListener(e -> {
            //开启aiMove时禁用撤销和保存按钮
            aiMoveButton.setEnabled(false);
            revokeButton.setEnabled(false);
            if(rlevel.getrGameState().getCurrentUserId() != null) {
                saveButton.setEnabled(false);
            }
            ((AiGameMode) ((GameLevel) rlevel).getrGameModeBase()).aiMove(getGamePanel().getPanelMap());
        });

        //move button panel包含四个按钮，上下左右
        movePanel.setLayout(null);
        mainPanel.add(movePanel);
        movePanel.setBounds(20, height *60/100, 300*80/100, 200*80/100);
        movePanel.setLayout(new GridLayout(2, 3));

        nothingButton.setVisible(false);
        nothingButton2.setVisible(false);
        movePanel.add(nothingButton);
        movePanel.add(upbtn);
        movePanel.add(nothingButton2);

        upbtn.addActionListener(e -> gamePanel.doMoveUp(gamePanel.getSelectedBlock(),true));
        downbtn.addActionListener(e -> gamePanel.doMoveDown(gamePanel.getSelectedBlock(),true));
        leftbtn.addActionListener(e -> gamePanel.doMoveLeft(gamePanel.getSelectedBlock(),true));
        rightbtn.addActionListener(e -> gamePanel.doMoveRight(gamePanel.getSelectedBlock(),true));
        movePanel.add(leftbtn);
        movePanel.add(downbtn);
        movePanel.add(rightbtn);
        //info panel 记录游戏开始时间和总共走的步数
        mainPanel.add(infoPanel);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // 垂直盒子布局
        infoPanel.setBounds(width *55/100, height *60/100, width /3, height /5);
        timeInfoPanel.setLayout(new GridLayout(3, 1, 5, 5));
        timeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.stepLabel = stepsLabel;
        timeInfoPanel.add(stepsLabel);
        timeLabel = new JLabel(formatTime(elapsedTime));
        timeInfoPanel.add(timeLabel);
        // 剩余时间标签（新增尺寸约束）
        remainingTimeLabel = new JLabel("剩余时间：--:--");
        remainingTimeLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        remainingTimeLabel.setForeground(new Color(0, 100, 0)); // 深绿色
        remainingTimeLabel.setMinimumSize(new Dimension(120, 25));
        remainingTimeLabel.setPreferredSize(new Dimension(120, 25));
        timeInfoPanel.add(remainingTimeLabel);
        infoPanel.add(timeInfoPanel);
        // 保存提示标签（单独一行）
        saveTipLabel = new JLabel("Game has been saved!");
        saveTipLabel.setVisible(false);
        saveTipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public void showModeSelection() {
        ModeSelectDialog dialog = new ModeSelectDialog(this);
        dialog.setVisible(true);
        MyGameState gameState = rlevel.getrGameState();
        gameState.setTimedMode(dialog.isTimedModeSelected());
        setupAudio();
        AudioManager.getInstance().setBgmEnabled(true);
        if (dialog.isTimedModeSelected()) {
            gameState.setRemainingTime(TIME_LIMIT);
            remainingTimeLabel.setVisible(true);
            remainingTimeLabel.setText("剩余时间：03:00"); // 初始时间设置为3分钟
            remainingTimeLabel.setForeground(Color.BLACK);
            startCountdown();
        } else {
            remainingTimeLabel.setVisible(false);
        }
    }


    public void initialGame() {
        gamePanel.initialGame();
        setTimer();//开始计时
        // 启动倒计时（如果有时限模式）

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

    // 新增方法：启动倒计时
    private void startCountdown() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        MyGameState gameState = rlevel.getrGameState();
        countdownTimer = new Timer(1000, e -> {
            long remaining = gameState.getRemainingTime() - 1000;
            gameState.setRemainingTime(remaining);
            SwingUtilities.invokeLater(() -> {
                long totalMs = Math.max(remaining, 0);
                long minutes = totalMs / (1000 * 60);
                long seconds = (totalMs % (1000 * 60)) / 1000;
                remainingTimeLabel.setText("剩余时间：" + String.format("%02d:%02d", minutes, seconds));

                // 强制刷新界面
                remainingTimeLabel.revalidate();
                remainingTimeLabel.repaint();

                if (remaining <= 60000) {
                    remainingTimeLabel.setForeground(Color.RED);
                    // ... 原有声音提示代码 ...
                }
                if (remaining <= 0) {
                    countdownTimer.stop();
                    gameOver(false);
                }
            });
        });
        countdownTimer.start();
    }
    // 新增方法：游戏结束处理
    private void gameOver(boolean isVictory) {
        if (gameTimer != null) gameTimer.stop();
        if (countdownTimer != null) countdownTimer.stop();

        if (isVictory) {
            AudioManager.getInstance().playSoundEffect(AudioManager.SoundEffectType.VICTORY);
            new VictoryDialog(this, elapsedTime).setVisible(true);
        } else {
            AudioManager.getInstance().playSoundEffect(AudioManager.SoundEffectType.ERROR);
            new DefaultDialog(this,"Time out!",true,
                    String.format("You spent %d steps.",rlevel.getrGameState().getMyLogSystem().getTotalSteps().size()),
                    String.format("You spent %s ",formatTime(elapsedTime))
                    ).setVisible(true);
        }
    }

    // 新增方法：控制背景音乐开关
    private void toggleBgm() {
        AudioManager audioManager = AudioManager.getInstance();
        boolean bgmEnabled = !audioManager.isBgmEnabled();
        audioManager.setBgmEnabled(bgmEnabled);
        // 更新按钮文本
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().startsWith("背景音乐:")) {
                ((JButton) comp).setText("背景音乐: " + (bgmEnabled ? "开" : "关"));
                break;
            }
        }
    }

    private void toggleSfx() {
        AudioManager audioManager = AudioManager.getInstance();
        boolean sfxEnabled = !audioManager.isSfxEnabled();
        audioManager.setSfxEnabled(sfxEnabled);
        // 更新按钮文本
        for (Component comp : infoPanel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().startsWith("音效:")) {
                ((JButton) comp).setText("音效: " + (sfxEnabled ? "开" : "关"));
                break;
            }
        }
    }

    // 新增方法：初始化音频
    private void setupAudio() {
        try {
            AudioManager.getInstance().generateBackgroundMusic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}




