package controller;

import frame.GamePanel;
import level.GameLevel;
import level.GameMap;
import level.LogSystem;

public class MyGameController extends GameControllerBase {
    private  GamePanel view;
    private  GameMap startMap;
    private  GameLevel gameLevel;
    private LogSystem myLogSystem;

    public MyGameController(GameLevel gameLevel, LogSystem myLogSystem) {
        this.gameLevel = gameLevel;
        this.myLogSystem = myLogSystem;
    }

    public void restartGame() {
        System.out.println("Do restart game here");
    }

    public boolean doMove(int row, int col, int direction) {
        //0右1左2上3下
        // 1. 验证起始位置有效性
        if (!isValidPosition(row, col)) {
            return false;
        }

        // 2. 获取当前方块信息
        int blockType = view.getPanelMap()[row][col];
        if (blockType == 0) {
            return false; // 不能移动空白区域
        }

        // 3. 获取方块尺寸（宽高）
        int[] size = getBlockSize(blockType);
        int width = size[0];
        int height = size[1];

        // 4. 计算移动方向增量
        int dx = 0, dy = 0;
        switch (direction) {
            case 0: dx = 1; break;
            case 1: dx = -1; break;
            case 2: dy = -1; break;
            case 3: dy = 1; break;
            default: return false;
        }

        // 5. 边界检测
        if (!canMoveBoundaryCheck(row, col, width, height, dx, dy)) {
            return false;
        }

        // 6. 碰撞检测
        if (!canMoveCollisionCheck(row, col, width, height, dx, dy)) {
            return false;
        }

        // 7. 执行移动
        performMove(row, col, width, height, dx, dy, blockType);
        return true;






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
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < startMap.getMapRow() &&
                col >= 0 && col < startMap.getMapCol();
    }

    // 辅助方法：获取方块尺寸
    private int[] getBlockSize(int blockType) {
        switch (blockType) {
            case 1: // 小兵（1x1）
                return new int[]{1, 1};
            case 7: // 曹操（2x2）
                return new int[]{2, 2};
            case 2:// 关羽(2*1)
                return new int[]{2,1};
            default: // 五虎将（1x2或2x1，需根据布局判断）
                // 这里假设五虎将都是1x2横向放置
                return new int[]{1, 2};
        }
    }

    // 边界检测
    private boolean canMoveBoundaryCheck(int row, int col,
                                         int width, int height,
                                         int dx, int dy) {
        // 计算移动后左上角新位置
        int newRow = row + dy;
        int newCol = col + dx;

        // 计算覆盖区域
        int endRow = newRow + height - 1;
        int endCol = newCol + width - 1;

        // 检查是否超出边界
        return newRow >= 0 && endRow < startMap.getMapRow() &&
                newCol >= 0 && endCol < startMap.getMapCol();
    }

    // 碰撞检测
    private boolean canMoveCollisionCheck(int row, int col,
                                          int width, int height,
                                          int dx, int dy) {
        // 遍历当前方块的所有格子
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                // 计算移动后的新位置
                int newR = r + dy;
                int newC = c + dx;

                // 检查新位置是否被其他方块占用
                if (view.getPanelMap()[newR][newC] != 0 &&
                        !isSameBlock(newR, newC, row, col, width, height)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 判断是否属于同一方块
    private boolean isSameBlock(int checkRow, int checkCol,
                                int baseRow, int baseCol,
                                int width, int height) {
        return checkRow >= baseRow &&
                checkRow < baseRow + height &&
                checkCol >= baseCol &&
                checkCol < baseCol + width;
    }

    // 执行实际移动操作
    private void performMove(int row, int col,
                             int width, int height,
                             int dx, int dy,
                             int blockType) {
        // 清除原位置
        for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                view.getPanelMap()[r][c] = 0;
            }
        }

        // 设置新位置
        int newRow = row + dy;
        int newCol = col + dx;
        for (int r = newRow; r < newRow + height; r++) {
            for (int c = newCol; c < newCol + width; c++) {
                view.getPanelMap()[r][c] = blockType;
            }
        }
        logStepInfo(col,row,newCol,newRow,blockType);//记录
        view.getSelectedBlock().setRow(newRow);//设置Block的位置
        view.getSelectedBlock().setCol(newCol);
        view.refreshSelectedBlock();//更新block
        view.repaint(); // 刷新界面
    }

    public void updateFrame() {
        this.view=gameLevel.getGameFrame().getGamePanel();
        this.startMap=gameLevel.getGameMap();
        view.setGameController(this);
    }

    private void logStepInfo(int startX, int startY, int endX, int endY,int id) {
        myLogSystem.addStep(startX,startY,endX,endY,id);
    }
}


