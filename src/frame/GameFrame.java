package frame;

import frame.block.*;
import level.LevelBase;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameFrame extends FrameBase {
    private GamePanel gamePanel;

    public GameFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.setLayout(new BorderLayout());
        gamePanel = new GamePanel();
        this.add(gamePanel, BorderLayout.CENTER);
        
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e ->gamePanel.restartGame());
        this.add(restartButton,BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
class GamePanel extends JPanel {

    private static final int CELL_SIZE = 60;
    private ArrayList<Block> blocks;
    public GamePanel() {
        blocks = createInitialBlocks();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Block block : blocks) {
            int x = block.getCol() * CELL_SIZE;
            int y = block.getRow() * CELL_SIZE;
            int width = block.getWidth() * CELL_SIZE;
            int height = block.getHeight() * CELL_SIZE;

            g.setColor(block.getColor());
            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }

    private ArrayList<Block> createInitialBlocks(){
        ArrayList<Block> blocks = new ArrayList<>();
        // 曹操（2x2）

        blocks.add(new Block(0, 1, 2, 2, Color.RED));
        // 关羽（2x1 竖放）
        blocks.add(new Block(0, 3, 1, 2, Color.BLUE));
        // 常规方块（1x2）
        blocks.add(new Block(2, 0, 2, 1, Color.GREEN));
        blocks.add(new Block(2, 3, 2, 1, Color.GREEN));
        blocks.add(new Block(3, 0, 2, 1, Color.GREEN));
        blocks.add(new Block(3, 3, 2, 1, Color.GREEN));
        // 士兵方块（1x1）
        blocks.add(new Block(0, 0, 1, 1, Color.YELLOW));
        blocks.add(new Block(0, 4, 1, 1, Color.YELLOW));
        blocks.add(new Block(2, 2, 1, 1, Color.YELLOW));
        blocks.add(new Block(3, 2, 1, 1, Color.YELLOW));
        return blocks;
    }

    public void restartGame() {

    }
}
