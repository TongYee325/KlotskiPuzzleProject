package frame;

import level.LevelBase;

import javax.swing.*;

public class GameFrame extends FrameBase {
    private JButton startGameBtn;
    public GameFrame(LevelBase level, String title, int width, int height) {
        super(level,title, width, height);
        this.setLayout(null);

    }
}
