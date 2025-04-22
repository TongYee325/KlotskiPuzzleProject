package frame;

import frame.block.Block;
import level.GameMap;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final int BLOCK_SIZE=60;
    private GameMap map;
    private Block selectedBlock;
    private ArrayList<Block> blocks;

    private static final int CELL_SIZE = 60;

    public GamePanel(GameMap gameMap) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.selectedBlock = null;
        map = gameMap;
        GridLayout layout = new GridLayout(map.getMapRow(), map.getMapCol());
        this.setLayout(layout);
        this.setBackground(Color.ORANGE);
        this.setSize(map.getMapCol()*BLOCK_SIZE+4, map.getMapRow()*BLOCK_SIZE+4);

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
    public void initialGame(){
        blocks = new ArrayList<Block>();
        if(map==null)return;
        int[][] mapIndex = map.getMapIndex();
        for(int x = 0; x<mapIndex.length; x++){
            for(int y = 0; y<mapIndex[x].length; y++){//根据地图数组初始化各个block的颜色和大小
                Block block = null;
                if(mapIndex[x][y]==0){
                    block = new Block(Color.gray,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==1){
                    block = new Block(Color.YELLOW,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==2){
                    block = new Block(Color.RED,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==3){
                    block = new Block(Color.ORANGE,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==4){
                    block = new Block(Color.GREEN,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==5){
                    block = new Block(Color.CYAN,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==6){
                    block = new Block(Color.MAGENTA,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }else if(mapIndex[x][y]==7){
                    block = new Block(Color.BLUE,x,y);
                    block.setSize(BLOCK_SIZE, BLOCK_SIZE);
                    mapIndex[x][y]=0;
                }
                this.add(block);
                blocks.add(block);
            }
        }
        this.repaint();
    }



}
