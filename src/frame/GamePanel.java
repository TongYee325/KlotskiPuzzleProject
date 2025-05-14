package frame;

import controller.MyGameController;
import controller.Position;
import frame.audio.AudioManager;
import frame.block.Block;
import frame.dialog.VictoryDialog;
import level.GameLevel;
import level.map.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    //胜利条件
    private final int TARGET_X = 1;
    private final int TARGET_Y = 3;

    private GameFrame rFrame;

    private MyGameController rController;

    private GameMap rMap;
    private int[][] panelMap;




    private Block selectedBlock;
    private Block CaoCaoBlock;
    private ArrayList<Block> blocks;
    private final int BLOCK_SIZE = 60;
    private boolean isGameOver = false; // 标记游戏是否结束


    public GamePanel(GameMap gameMap, GameFrame rFrame) {
        this.rFrame = rFrame;
        this.rController = ((GameLevel) this.rFrame.getRlevel()).getController();
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.selectedBlock = null;
        this.rMap = gameMap;
        this.panelMap = new int[rMap.getMapRow()][rMap.getMapCol()];
        this.setBackground(Color.ORANGE);
        this.setBounds(20, 20, rMap.getMapCol() * BLOCK_SIZE + 4, rMap.getMapRow() * BLOCK_SIZE + 4);

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
                    box = new Block(Color.RED, i, j, 5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.PINK, i, j, 6);
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
                    box = new Block(Color.RED, i, j, 5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.PINK, i, j, 6);
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
                afterMove();
            }
        }
    }

    protected void doMoveLeft(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 1, needLog)) {
                afterMove();
            }
        }
    }

    protected void doMoveUp(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 2, needLog)) {
                afterMove();
            }
        }
    }

    protected void doMoveDown(Block movedBlock, boolean needLog) {
        if (isGameOver) { // 检查游戏是否结束
            return;
        }
        if (movedBlock != null) {
            if (rController.doMove(movedBlock, movedBlock.getRow(), movedBlock.getCol(), 3, needLog)) {
                afterMove();
            }
        }
    }

    protected void afterMove() {
        rFrame.updateStep();
        if (CaoCaoBlock.getRow() == TARGET_Y && CaoCaoBlock.getCol() == TARGET_X) {
            // 停止游戏计时
            rFrame.stopTimer();
            // 播放移动音效
            AudioManager.getInstance().playSoundEffect(AudioManager.SoundEffectType.MOVE);

            if (CaoCaoBlock.getRow() == TARGET_Y && CaoCaoBlock.getCol() == TARGET_X) {
                // 播放胜利音效
                AudioManager.getInstance().playSoundEffect(AudioManager.SoundEffectType.VICTORY);
            }

            // 弹出胜利对话框（传入当前 GameFrame）
            SwingUtilities.invokeLater(() -> {
                VictoryDialog victoryDialog = new VictoryDialog(rFrame);
                victoryDialog.setVisible(true);
            });
            isGameOver = true; // 设置游戏结束标记

            // 原有日志输出
            /*System.out.println("You Win!");
            rFrame.getRlevel().getrGameState().getMyLogSystem().printAllSteps();
            rFrame.getRlevel().getrGameState().getMyLogSystem().printStepsNum();*/
        }
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
