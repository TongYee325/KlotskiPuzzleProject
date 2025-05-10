package controller;

import java.io.Serializable;

public class Path implements Serializable {
    private final Position start;
    private final Position end;
    public Path(Position start, Position end) {
        this.start = start;
        this.end = end;
    }


    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    public String toString() {
        return start.toString() + " -> " + end.toString();
    }

    public String toString(boolean castToXY)//castToXY：是否转为xy轴模式
    {
        if(castToXY) return (
                (start.getX()+1) +","+(start.getY()+1) +" -> " + (end.getX()+1) +","+(end.getY()+1)
    );
        return start.toString() + " -> " + end.toString();
    }

}
