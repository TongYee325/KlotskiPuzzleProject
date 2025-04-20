package frame;


import level.LevelBase;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends FrameBase {

    private JButton startGameBtn;

    public MenuFrame(LevelBase level, String title, int width, int height) {
        super(level, title, width, height);
        this.setLayout(null);
        Point center = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point btnLocation = new Point(center.x, center.y - height / 3);
        startGameBtn = FrameUtil.createButton(this, "StartGame", new Point((int) (btnLocation.getX() - 50), (int) btnLocation.getY()), 100, 40);
        startGameBtn.addActionListener(e -> {
            level.nextLevel();
        });

    }
}
