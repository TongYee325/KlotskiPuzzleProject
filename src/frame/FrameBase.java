package frame;


import level.LevelBase;

import javax.swing.*;

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
    }

    public void setInMid() {
        this.x = (FrameUtil.getScreenDimensions()[0] - width) / 2;
        this.y = (FrameUtil.getScreenDimensions()[1] - height) / 2;
        setLocation(x, y);
    }

    public void update() {

    }

    public void Init() {

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
