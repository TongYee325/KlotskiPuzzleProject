package frame;

import audio.AudioManager;
import controller.MyGameController;
import frame.block.Block;
import frame.dialog.DefaultDialog;
import gamestate.MyGameState;
import level.GameLevel;
import level.map.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    //胜利条件
    private final int TARGET_X = 1;
    private final int TARGET_Y = 3;

    private GameFrame rFrame;

    private MyGameController rController;

    private GameMap rMap;
    private int[][] panelMap;

    private long totalTime;  // 总游戏时间（毫秒）


    private int totalSteps;  // 总步数



    private Block selectedBlock;
    private Block CaoCaoBlock;
    private ArrayList<Block> blocks;
    private final int BLOCK_SIZE = 60;
    private boolean isGameOver = false; // 标记游戏是否结束

    //fx
    private AudioManager audioManager;
    private final String fxPath = "./fx/slideFX.wav";

    public GamePanel(GameMap gameMap, GameFrame rFrame) {
        this.rFrame = rFrame;
        this.rController = ((GameLevel) this.rFrame.getRlevel()).getController();
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setFocusable(true);
        this.setVisible(true);
        this.setLayout(null);
        this.selectedBlock = null;
        this.rMap = gameMap;
        this.panelMap = new int[rMap.getMapRow()][rMap.getMapCol()];
        this.setBackground(Color.ORANGE);
        this.setBounds(20, 20, rMap.getMapCol() * BLOCK_SIZE + 4, rMap.getMapRow() * BLOCK_SIZE + 4);

        this.audioManager = new AudioManager();


        // 确保面板可获取焦点
        this.requestFocusInWindow();
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                requestFocusInWindow(); // 失去焦点后重新请求
            }
        });


    }


    /** 将毫秒时间格式化为 "分:秒" 或 "时:分:秒" */
    private String formatTime(long elapsedTime) {
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = (elapsedTime / (1000 * 60)) % 60;
        long hours = elapsedTime / (1000 * 60 * 60);

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d秒", seconds);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.setBorder(border);

    }

    /*{3,7,7,4},
    {3,7,7,4},
    {5,2,2,6},
    {5,1,1,6},
    {1,0,0,1},*/

    //初始化游戏，根据给定地图生成对应图形。若传入panelMap地图，则关卡为加载游戏形式。
    public void initialGame() {
        this.requestFocusInWindow();


        blocks = new ArrayList<Block>();
        //copy a map
        int[][] mapIndex = new int[rMap.getMapRow()][rMap.getMapCol()];
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                mapIndex[i][j] = rMap.getMapID(i, j);
                panelMap[i][j] = rMap.getMapID(i, j);
            }
        }
        //build Component
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                Block box = null;
                if (mapIndex[i][j] == 1) {
                    box = new Block(Color.ORANGE, i, j, 1);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                } else if (mapIndex[i][j] == 2) {
                    box = new Block(Color.PINK, i, j, 2);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                    mapIndex[i][j + 1] = 0;
                } else if (mapIndex[i][j] == 3) {
                    box = new Block(Color.YELLOW, i, j, 3);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 4) {
                    box = new Block(Color.cyan, i, j, 4);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 5) {
                    box = new Block(Color.MAGENTA, i, j, 5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.GREEN, i, j, 6);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 7) {
                    box = new Block(Color.BLUE, i, j, 7);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                    mapIndex[i][j + 1] = 0;
                    mapIndex[i + 1][j + 1] = 0;
                    CaoCaoBlock = box;
                }
                if (box != null) {
                    box.setLocation(box.getCol() * BLOCK_SIZE + 2, box.getRow() * BLOCK_SIZE + 2);
                    blocks.add(box);
                    this.add(box);
                }
            }
        }
        repaint();
    }

    public void initialGame(int[][] panelMap) {
        this.panelMap = panelMap;
        this.requestFocusInWindow();

        blocks = new ArrayList<Block>();
        //copy a map
        int[][] mapIndex = new int[panelMap.length][panelMap[0].length];
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                mapIndex[i][j] = panelMap[i][j];
            }
        }
        //build Component
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                Block box = null;
                if (mapIndex[i][j] == 1) {
                    box = new Block(Color.ORANGE, i, j, 1);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                } else if (mapIndex[i][j] == 2) {
                    box = new Block(Color.PINK, i, j, 2);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                    mapIndex[i][j + 1] = 0;
                } else if (mapIndex[i][j] == 3) {
                    box = new Block(Color.YELLOW, i, j, 3);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 4) {
                    box = new Block(Color.cyan, i, j, 4);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 5) {
                    box = new Block(Color.MAGENTA, i, j, 5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.GREEN, i, j, 6);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 7) {
                    box = new Block(Color.BLUE, i, j, 7);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                    mapIndex[i][j + 1] = 0;
                    mapIndex[i + 1][j + 1] = 0;
                    CaoCaoBlock = box;
                }
                if (box != null) {
                    box.setLocation(box.getCol() * BLOCK_SIZE + 2, box.getRow() * BLOCK_SIZE + 2);
                    blocks.add(box);
                    this.add(box);
                }
            }
        }
        repaint();
    }

    public void refreshMovedBlock(Block movedBlock) {
        if (movedBlock != null) {
            movedBlock.moveTo(movedBlock.getCol() * BLOCK_SIZE + 2, movedBlock.getRow() * BLOCK_SIZE + 2);
        }
    }

    //处理鼠标键盘事件响应
    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        this.requestFocusInWindow();
        //System.out.println(e.getKeyChar());
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> doMoveRight(selectedBlock,true);
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> doMoveLeft(selectedBlock,true);
                case KeyEvent.VK_UP, KeyEvent.VK_W -> doMoveUp(selectedBlock,true);
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> doMoveDown(selectedBlock,true);
            }
        }
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            doMousePressed(e.getPoint());
        }
    }

    protected void doMousePressed(Point p) {
        Component component = this.getComponentAt(p);
        if (component instanceof Block clickedComponent) {
            if (selectedBlock == null) {
                selectedBlock = clickedComponent;
                selectedBlock.setSelected(true);
            } else if (selectedBlock != clickedComponent) {
                selectedBlock.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBlock = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBlock = null;
            }
        }
    }

    //移动逻辑，通过调用controller中doMove函数实现
    protected void doMoveRight(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 0, needLog)) {
                rFrame.updateStep();
            }
        }
    }

    protected void doMoveLeft(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 1, needLog)) {
                rFrame.updateStep();
            }
        }
    }

    protected void doMoveUp(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 2, needLog)) {
                rFrame.updateStep();
            }
        }
    }

    protected void doMoveDown(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 3, needLog)) {
                rFrame.updateStep();
            }
        }
    }

    protected void afterMove() {
        playFx();
        if (CaoCaoBlock.getRow() == TARGET_Y && CaoCaoBlock.getCol() == TARGET_X) {
            // 停止游戏计时
            rFrame.stopTimer();
            rFrame.stopRemainTimer();

            updateStepsAndTotalTime();
            /*this.remainingTime = rFrame.getRlevel().getrGameState().getRemainingTime();*/

            // 弹出胜利对话框（传入当前 GameFrame）
            showWinInfo();
        }
    }

    public void updateStepsAndTotalTime(){
        this.totalSteps = rFrame.getRlevel().getrGameState().getMyLogSystem().getTotalSteps().size();
        this.totalTime = rFrame.getElapsedTime();
    }

    private void showWinInfo() {
        SwingUtilities.invokeLater(() -> {
            // 构建胜利对话框的中间内容面板（统计信息）
            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));

            // 总步数面板
            JPanel stepsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel stepsLabel = new JLabel("Total Steps:");
            stepsLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            JTextField stepsField = new JTextField(String.valueOf(totalSteps), 15);
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
            JTextField timeField = new JTextField(formatTime(totalTime), 15);
            timeField.setEditable(false);
            timeField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            timeField.setBackground(Color.WHITE);
            timeField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
            timePanel.add(timeLabel);
            timePanel.add(timeField);

            // 剩余时间面板（限时模式）
            if (rFrame.getRlevel().getrGameState().isTimedMode()) {
                JPanel remainingTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel remainingTimeLabel = new JLabel("Remaining Time:");
                remainingTimeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
                JTextField remainingTimeField = new JTextField(formatTime(totalTime), 10);
                remainingTimeField.setEditable(false);
                remainingTimeField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                remainingTimeField.setBackground(Color.WHITE);
                remainingTimeField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
                remainingTimePanel.add(remainingTimeLabel);
                remainingTimePanel.add(remainingTimeField);
                contentPanel.add(remainingTimePanel, BorderLayout.SOUTH);
            }

            contentPanel.add(stepsPanel, BorderLayout.NORTH);
            contentPanel.add(timePanel, BorderLayout.CENTER);

            // 调用 DefaultDialog
            new DefaultDialog(
                    rFrame,
                    "Victory!",
                    true,
                    "You Win!",
                    contentPanel,
                    "OK",
                    new Dimension(450, 250)
            );
        });
    }

    private void playFx() {
        audioManager.setVolume(rFrame.getRlevel().getrGameState().getMusicVolume());
        audioManager.play(fxPath,false);
    }

    //撤销逻辑，通过复用移动逻辑实现，、
    public void revoke() {
        //撤销功能
        Block lastMovedBlock=null;
        int lastMovedDirection = -1;
        lastMovedBlock=rFrame.getRlevel().getrGameState().getMyLogSystem().getLastMovedBlock();
        lastMovedDirection=rFrame.getRlevel().getrGameState().getMyLogSystem().getLastMovedDirection();

        if (lastMovedDirection != -1 && lastMovedBlock != null) {
            switch (lastMovedDirection) {
                case 0:
                    doMoveLeft(lastMovedBlock,false);
                    break;
                case 1:
                    doMoveRight(lastMovedBlock,false);
                    break;
                case 2:
                    doMoveDown(lastMovedBlock,false);
                    break;
                case 3:
                    doMoveUp(lastMovedBlock,false);
                    break;
                default:
                    break;

            }
        }
        rFrame.getRlevel().getrGameState().getMyLogSystem().revoke();
        this.requestFocusInWindow();
        rFrame.updateStep();
    }








    //Getter & Setter --------------------------------------------------------------------------------------------------
    public MyGameController getrController() {
        return rController;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setSelectedBlock(Block selectedBlock) {
        this.selectedBlock = selectedBlock;
    }

    public Block getSelectedBlock() {
        return selectedBlock;
    }

    public int[][] getPanelMap() {
        return panelMap;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setrController(MyGameController rController) {
        this.rController = rController;
    }

}
