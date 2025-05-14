package controller;


public final class Position implements Comparable<Position> {
    int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + ", " + y;
    }

    @Override
    public int compareTo( Position another) {
        if (this.x < another.x) return -1;
        else if (this.x == another.x) {
            return Integer.compare(this.y, another.y);
        } else {
            return 1;
        }
    }
}