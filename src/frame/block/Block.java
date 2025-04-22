package frame.block;

import java.awt.*;

public class Block {
    private int row;
    private int col;
    private int width;
    private int height;
    private Color color;

    public Block(int row, int col, int width, int height, Color color) {
        this.row = row;
        this.col = col;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
}
