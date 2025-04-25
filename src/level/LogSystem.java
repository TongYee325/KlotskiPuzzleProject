package level;

import java.util.ArrayList;

public class LogSystem {
    private ArrayList<Step> totalSteps;
    public LogSystem() {
        totalSteps = new ArrayList<Step>();

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

}
class Step{
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
