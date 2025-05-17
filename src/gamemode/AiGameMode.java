package gamemode;

import controller.AiController;
import controller.MyGameController;
import controller.Path;
import frame.GamePanel;
import frame.block.Block;
import level.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AiGameMode extends GameModeBase {
    private AiController ai;
    private MyGameController myGameController;
    private GameLevel gameLevel;
    private GamePanel view;
    private Timer moveTimer;
    private int currentStepIndex = 0;

    public AiGameMode(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
        this.ai = new AiController();
        this.myGameController = new MyGameController(gameLevel,gameLevel.getrGameState().getMyLogSystem());
    }

    public List<Path> solveMap(int[][] mapToSolve) {
        return ai.solve(mapToSolve);
    }

    public void aiMove(int[][] map) {
        this.view =gameLevel.getGameFrame().getGamePanel();
        myGameController.updateControlledPanelAccordingToLevel();
        ArrayList<Block> blocks = view.getBlocks();
        List<Path> solvePaths = solveMap(map);
        executeSteps(solvePaths, blocks);
    }

    public void executeSteps(List<Path> solvePaths,ArrayList<Block> blocks) {
        currentStepIndex = 0; // 重置索引
        if (moveTimer != null) {
            moveTimer.stop();
        }

        moveTimer = new Timer(200, e -> {
            if (currentStepIndex < solvePaths.size()) {
                Path p = solvePaths.get(currentStepIndex);

                // 执行步骤
                selectBlock(getBlockFromMap(p.getStart().getX(), p.getStart().getY(), blocks));
                moveSelectedBlock(p.getDir());

                currentStepIndex++; // 移动到下一步
            } else {
                // 所有步骤执行完毕
                ((Timer)e.getSource()).stop();
                System.out.println("所有移动步骤完成");
            }
        });

        moveTimer.setInitialDelay(0); // 立即开始
        moveTimer.start();
    }

    public void selectBlock(Block block) {
        Block selectedBlock = view.getSelectedBlock();
        if (!block.equals(selectedBlock)) {
            if(selectedBlock != null) {
                selectedBlock.setSelected(false);
                block.setSelected(true);
                selectedBlock = block;
                view.setSelectedBlock(selectedBlock);
            }else {
                selectedBlock = block;
                selectedBlock.setSelected(true);
                view.setSelectedBlock(selectedBlock);
            }
        } else {
            view.setSelectedBlock(selectedBlock);
        }
    }

    public void moveSelectedBlock(int dir) {
        //0右1左2上3下
        Block selectedBlock = view.getSelectedBlock();
        if (selectedBlock != null) {
            if(myGameController.doMove(selectedBlock, selectedBlock.getRow(), selectedBlock.getCol(), dir, true))
            {
                gameLevel.getGameFrame().updateStep();
            }
            gameLevel.getGameFrame().updateStep();
        }
    }


    public Block getBlockFromMap(int mapRow, int mapCol, ArrayList<Block> blocks) {
        for (Block block : blocks) {
            if (block.getRow() == mapRow && block.getCol() == mapCol) {
                return block;
            }
        }
        System.out.println("mapRow: " + mapRow + ", mapCol: " + mapCol);
        return null;
    }
}
