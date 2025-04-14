package hud;

import javax.swing.*;

public class HUDBase extends JFrame {
    protected int width=600;
    protected int height=600;
    protected String title="default title";

    public HUDBase(String title) {
        if(title!=null) this.title=title;
        setTitle(this.title);//设置窗口标题
        setSize(width, height);//设置尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认窗口可以关闭
        setVisible(true);//设置窗口是否可见
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


}
