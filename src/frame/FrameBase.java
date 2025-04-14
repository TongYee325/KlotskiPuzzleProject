package frame;

import javax.swing.*;

public class FrameBase extends JFrame {
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected String title="default title";

    public FrameBase(String title, int width, int height) {
        if(title!=null) this.title=title;
        setTitle(this.title);//设置窗口标题
        this.width=width;
        this.height=height;
        setSize(width, height);//设置尺寸
        setInMid();//默认居中
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认窗口可以关闭
        setVisible(true);//设置窗口是否可见
    }
    public void setInMid()
    {
        this.x=(FrameUtil.getScreenDimensions()[0]-width)/2;
        this.y=(FrameUtil.getScreenDimensions()[1]-height)/2;
        setLocation(x,y);
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
