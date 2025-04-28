package frame;

import controller.MyGameController;
import frame.block.Block;
import level.GameLevel;
import level.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    //胜利条件
    private final int TARGET_X=1;
    private final int TARGET_Y=3;

    private GameFrame rFrame;



    private MyGameController rController;


    private GameMap rMap;
    private int[][] panelMap;

    private Block selectedBlock;
    private Block CaoCaoBlock;
    private ArrayList<Block> blocks;
    private final int BLOCK_SIZE = 60;

    public GamePanel(GameMap gameMap,GameFrame rFrame) {
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
        this.panelMap =new int[rMap.getMapRow()][rMap.getMapCol()];
        this.setBackground(Color.ORANGE);
        this.setBounds(20,20, rMap.getMapCol() * BLOCK_SIZE + 4, rMap.getMapRow() * BLOCK_SIZE + 4);

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
                    box = new Block(Color.ORANGE, i, j,1);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                } else if (mapIndex[i][j] == 2) {
                    box = new Block(Color.PINK, i, j,2);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                    mapIndex[i][j + 1] = 0;
                } else if (mapIndex[i][j] == 3) {
                    box = new Block(Color.YELLOW, i, j,3);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 4) {
                    box = new Block(Color.cyan, i, j,4);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 5) {
                    box = new Block(Color.RED, i, j,5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.PINK, i, j,6);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }  else if (mapIndex[i][j] == 7) {
                    box = new Block(Color.BLUE, i, j,7);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                    mapIndex[i][j + 1] = 0;
                    mapIndex[i + 1][j + 1] = 0;
                    CaoCaoBlock =box;
                }
                if (box != null) {
                    box.setLocation(box.getCol()* BLOCK_SIZE + 2, box.getRow()* BLOCK_SIZE + 2);
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
                    box = new Block(Color.ORANGE, i, j,1);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                } else if (mapIndex[i][j] == 2) {
                    box = new Block(Color.PINK, i, j,2);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                    mapIndex[i][j + 1] = 0;
                } else if (mapIndex[i][j] == 3) {
                    box = new Block(Color.YELLOW, i, j,3);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 4) {
                    box = new Block(Color.cyan, i, j,4);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 5) {
                    box = new Block(Color.RED, i, j,5);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.PINK, i, j,6);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }  else if (mapIndex[i][j] == 7) {
                    box = new Block(Color.BLUE, i, j,7);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                    mapIndex[i][j + 1] = 0;
                    mapIndex[i + 1][j + 1] = 0;
                    CaoCaoBlock =box;
                }
                if (box != null) {
                    box.setLocation(box.getCol()* BLOCK_SIZE + 2, box.getRow()* BLOCK_SIZE + 2);
                    blocks.add(box);
                    this.add(box);
                }
            }
        }
        repaint();
    }



    public void refreshSelectedBlock() {
        if(selectedBlock != null) {
            selectedBlock.setLocation(selectedBlock.getCol()* BLOCK_SIZE + 2, selectedBlock.getRow()* BLOCK_SIZE + 2);
        }
    }






    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> doMoveRight();
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> doMoveLeft();
                case KeyEvent.VK_UP, KeyEvent.VK_W -> doMoveUp();
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> doMoveDown();
            }
        }
    }
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID()==MouseEvent.MOUSE_PRESSED) {
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

    protected void doMoveRight() {
        if (selectedBlock != null) {
            if (rController.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 0)) {
                afterMove();
            }
        };
    }

    protected void doMoveLeft() {
        if (selectedBlock != null) {
            if (rController.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 1)) {
                afterMove();
            }
        }
    }

    protected void doMoveUp() {
        if (selectedBlock != null) {
            if (rController.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 2)) {
                afterMove();
            }
        }
    }

    protected void doMoveDown() {
        if (selectedBlock != null) {
            if (rController.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 3)) {
                afterMove();
            }
        }

    }

    protected void afterMove() {
        if(CaoCaoBlock.getRow()==TARGET_Y && CaoCaoBlock.getCol()==TARGET_X) {
            System.out.println("You Win!");
            rFrame.getRlevel().getrGameState().getMyLogSystem().printAllSteps();
            rFrame.getRlevel().getrGameState().getMyLogSystem().printStepsNum();
        }

    }







    public MyGameController getrController() {
        return rController;
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
