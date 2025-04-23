package controller;

import frame.GamePanel;
import level.GameMap;

public class MyGameController extends GameControllerBase {
    private final GamePanel view;
    private final GameMap startMap;

    public MyGameController(GamePanel view, GameMap map) {
        this.view = view;
        this.startMap = map;
        view.setGameController(this);
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }

    public boolean doMove(int row, int col, int direction) {//0右1左2上3下
        int[][] temp = view.getPanelMap();
        //TODO!!!!






        /*if (map.getMapID(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    box.setRow(nextRow);
                    box.setCol(nextCol);
                    box.setLocation(box.getCol() * view.getGRID_SIZE() + 2, box.getRow() * view.getGRID_SIZE() + 2);
                    box.repaint();
                    return true;
                }
            }
        }*/
        return false;
    }
}

