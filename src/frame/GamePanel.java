package frame;

import controller.MyGameController;
import frame.block.Block;
import level.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {


    private MyGameController controller;
    private final int BLOCK_SIZE = 60;
    private GameMap map;

    public int[][] getPanelMap() {
        return panelMap;
    }

    private int[][] panelMap;
    private Block selectedBlock;
    private ArrayList<Block> blocks;

    private static final int CELL_SIZE = 60;

    public GamePanel(GameMap gameMap) {
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.setFocusable(true);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.selectedBlock = null;
        map = gameMap;
        panelMap =new int[map.getMapRow()][map.getMapCol()];
        this.setBackground(Color.ORANGE);
        this.setBounds(20,20,map.getMapCol() * BLOCK_SIZE + 4, map.getMapRow() * BLOCK_SIZE + 4);

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

        blocks = new ArrayList<Block>();
        //copy a map
        int[][] mapIndex = new int[map.getMapRow()][map.getMapCol()];
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                mapIndex[i][j] = map.getMapID(i, j);
                panelMap[i][j] = map.getMapID(i, j);
            }
        }
        //build Component
        for (int i = 0; i < mapIndex.length; i++) {
            for (int j = 0; j < mapIndex[0].length; j++) {
                Block box = null;
                if (mapIndex[i][j] == 1) {
                    box = new Block(Color.ORANGE, i, j);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                } else if (mapIndex[i][j] == 2) {
                    box = new Block(Color.PINK, i, j);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE);
                    mapIndex[i][j] = 0;
                    mapIndex[i][j + 1] = 0;
                } else if (mapIndex[i][j] == 3) {
                    box = new Block(Color.YELLOW, i, j);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 4) {
                    box = new Block(Color.cyan, i, j);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                } else if (mapIndex[i][j] == 5) {
                    box = new Block(Color.RED, i, j);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }else if (mapIndex[i][j] == 6) {
                    box = new Block(Color.PINK, i, j);
                    box.setSize(BLOCK_SIZE, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                }  else if (mapIndex[i][j] == 7) {
                    box = new Block(Color.BLUE, i, j);
                    box.setSize(BLOCK_SIZE * 2, BLOCK_SIZE * 2);
                    mapIndex[i][j] = 0;
                    mapIndex[i + 1][j] = 0;
                    mapIndex[i][j + 1] = 0;
                    mapIndex[i + 1][j + 1] = 0;
                }
                if (box != null) {
                    box.setLocation(j * BLOCK_SIZE + 2, i * BLOCK_SIZE + 2);
                    blocks.add(box);
                    this.add(box);
                }
            }
        }
        repaint();
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
        } else if(e.getID()==MouseEvent.MOUSE_RELEASED) {
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
            if (controller.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 0)) {
                afterMove();
            }
        };
    }
    protected void doMoveLeft() {
        if (selectedBlock != null) {
            if (controller.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 1)) {
                afterMove();
            }
        }
    }
    protected void doMoveUp() {
        if (selectedBlock != null) {
            if (controller.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 2)) {
                afterMove();
            }
        }
    }
    protected void doMoveDown() {
        if (selectedBlock != null) {
            if (controller.doMove(selectedBlock.getRow(), selectedBlock.getCol(), 3)) {
                afterMove();
            }
        }

    }

    protected void afterMove() {}



    public MyGameController getGameController() {
        return controller;
    }

    public void setGameController(MyGameController gameController) {
        this.controller = gameController;
    }
}
