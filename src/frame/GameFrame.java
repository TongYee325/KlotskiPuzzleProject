package frame;

import frame.block.*;
import level.LevelBase;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GameFrame extends FrameBase {
    private GamePanel gamePanel;

    public GameFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.setLayout(new BorderLayout());
        gamePanel = new GamePanel();
        this.add(gamePanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        //todo
        this.add(restartButton, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class GamePanel extends JPanel {

    private static final int CELL_SIZE = 60;

    public GamePanel() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //todo
    }
}


