package frame.block;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Block extends JComponent {
    private boolean isMoving = false;
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;
    private Animator anim;


    private int id;


    public Block(Color color, int row, int col,int id) {
        super();
        this.anim = new Animator();
        this.id =id;
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        Border border ;
        if(isSelected){
            border = BorderFactory.createLineBorder(Color.red,2);
        }else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        }
        this.setBorder(border);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();
    }


    public void moveTo(int x ,int y )
    {
        System.out.println("From "+getLocation().x+" "+getLocation().y);
        System.out.println("Move to "+x+" "+y);
        if(isMoving)
        {
            anim.stopAnimation();
            setLocation(x,y);
            repaint();
            return;
        }
        isMoving = true;
        anim.startAnimation(getLocation(),x,y);

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getId() {
        return id;
    }

    private class Animator implements ActionListener{
        private final int FRESH_DURATION = 16;//每16ms刷新一次，即60fps
        private float count = 0.0f;
        private final Timer timer = new Timer(FRESH_DURATION,this);
        private Point targetPoint;
        private Point startPoint;

        public void startAnimation(Point startPoint,int endx , int  endy) {
            this.startPoint = startPoint;
            targetPoint = new Point(endx, endy);
            System.out.println("startPoint "+startPoint.x+" "+startPoint.y);
            System.out.println("targetPoint "+targetPoint.x+" "+targetPoint.y);
            if(!timer.isRunning()) {
                timer.start();
            }
        }

        public void stopAnimation() {
            timer.stop();
            count = 0.0f;
            startPoint = null;
            targetPoint = null;
            isMoving = false;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            count +=0.1f; // 调整此值改变动画速度
            if (count>=1.1f) {
                count = 0.0f;
                timer.stop();
                startPoint=null;
                setLocation(targetPoint.x,targetPoint.y);
                targetPoint=null;
                isMoving = false;
            }else{
                setLocation((int) (startPoint.x+(targetPoint.x-startPoint.x)*count), (int) (startPoint.y+(targetPoint.y-startPoint.y)*count));
                repaint();
            }
        }
    }
}
