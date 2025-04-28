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

    public void clearSteps(){
        totalSteps.clear();
    }

    public void setTotalSteps(ArrayList<Step> totalSteps) {
        this.totalSteps = totalSteps;
    }

    public ArrayList<Step> getTotalSteps() {
        return totalSteps;
    }

}
