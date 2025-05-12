package frame;


import level.LevelBase;

import javax.swing.*;
import java.awt.*;

public class FrameBase extends JFrame {
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected String title = "default title";
    private LevelBase rlevel;

    public FrameBase(LevelBase level, String title, int width, int height) {
        super(title);
        rlevel = level;
        if (title != null) this.title = title;
        this.width = width;
        this.height = height;
        setSize(width, height);//设置尺寸
        setInMid();//默认居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认窗口可以关闭
        setVisible(true);//设置窗口是否可见
        setResizable(false);
    }

    public void setInMid() {
        this.x = (FrameUtil.getScreenDimensions()[0] - width) / 2;
        this.y = (FrameUtil.getScreenDimensions()[1] - height) / 2;
        setLocation(x, y);
    }

    public void setButtonBackground(JButton button,String normalPath) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        try {
            ImageIcon icon = new ImageIcon(normalPath);
            button.setIcon(icon);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setButtonBackground(JButton button,String normalPath,String rolloverPath) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        try {
            ImageIcon normalIcon = new ImageIcon(normalPath);
            ImageIcon roverIcon = new ImageIcon(rolloverPath);

            button.setIcon(normalIcon);
            button.setRolloverIcon(roverIcon);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setButtonBackground(JButton button,String normalPath,String rolloverPath,String pressPath) {
        button.setVisible(true);
        button.setEnabled(true);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        try {
            ImageIcon normalIcon = new ImageIcon(normalPath);
            ImageIcon roverIcon = new ImageIcon(rolloverPath);
            ImageIcon pressIcon = new ImageIcon(pressPath);

            button.setIcon(normalIcon);
            button.setRolloverIcon(roverIcon);
            button.setPressedIcon(pressIcon);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setBackground(String imgPath) {
        //设置背景
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
        ImageIcon icon = new ImageIcon(imgPath); // 创建背景图片对象
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        this.getContentPane().add(lblBackground); // 将组件添加到面板中
    }


    public void clear() {
        if (rlevel != null) {
            rlevel = null;
        }

        setVisible(false);
        dispose();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public LevelBase getRlevel() {
        return rlevel;
    }
}
