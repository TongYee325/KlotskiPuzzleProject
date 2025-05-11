package frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImagePanel extends JPanel {
    private Image image;
    private BufferedImage backgroundImage;

    public ImagePanel(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            System.err.println("背景图片加载失败: " + imagePath);
            backgroundImage = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制背景图片（拉伸填充 Panel）
        if(backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false; // 允许透明组件重叠绘制
    }
}

