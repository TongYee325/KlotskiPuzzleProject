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

    //转为int型direction信息，便于与撤回算法统一
    public int castToDirection() {
        if(startx==endx && endy>starty) {
            //down
            return 3;
        }else if(startx==endx && endy<starty) {
            //up
            return 2;
        }else if(endx>startx && endy==starty) {
            //right
            return 0;
        }else if(endx<startx && endy==starty) {
            //left
            return 1;
        }else{
            return -1;
        }
    }

}
