package level;

import java.io.Serializable;

public class Step implements Serializable {
    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private int id;
    public Step(int startx,int starty, int endx,int endy,int id) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(" %d from [%d,%d] to [%d,%d]", id,startx,starty, endx,endy);
    }
}
