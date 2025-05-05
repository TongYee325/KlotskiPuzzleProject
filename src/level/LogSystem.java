package level;

import frame.block.Block;

import java.util.ArrayList;
import java.util.logging.ErrorManager;

public class LogSystem {


    private long elapsedTime;
    private ArrayList<Step> totalSteps;
    private ArrayList<Block> movedBlocks;


    public LogSystem() {
        totalSteps = new ArrayList<Step>();
        movedBlocks = new ArrayList<Block>();

    }

    public void addStep(int startx, int starty,int endx,int endy,int id) {
        totalSteps.add(new Step(startx, starty, endx, endy, id));
    }

    public void printAllSteps(){
        for(Step step : totalSteps){
            System.out.println(step.toString());
        }
    }

    public void printStepsNum(){
        System.out.println("Total Steps: "+totalSteps.size());
    }

    public void clearSteps(){
        totalSteps.clear();
    }

    public void addBlock(Block block) {
        movedBlocks.add(block);
    }

    public Block getLastMovedBlock() {
        if(movedBlocks.isEmpty()){return null;}
        return movedBlocks.getLast();
    }

    public int getLastMovedDirection() {
        if(movedBlocks.isEmpty()||totalSteps.isEmpty()){return -1;}
        return totalSteps.getLast().castToDirection();
    }

    public void revoke()
    {
        if(movedBlocks.isEmpty()||totalSteps.isEmpty()){return;}
        totalSteps.removeLast();
        movedBlocks.removeLast();
    }


    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setTotalSteps(ArrayList<Step> totalSteps) {
        this.totalSteps = totalSteps;
    }

    public ArrayList<Step> getTotalSteps() {
        return totalSteps;
    }



}
