import controller.*;
import gamestate.MyGameState;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //KGameMode gameMode = new KGameMode();
            MyGameState myGameState = new MyGameState();
            myGameState.startLevel(0);
            //helloworld
        });
        /*int  [][]map ={
                {3,7,7,4},
                {3,7,7,4},
                {5,2,2,6},
                {5,1,1,6},
                {1,0,0,1}
        };
        AiController controller = new AiController();
        List<Path> result = controller.solve(map);
        System.out.println(result.size());
        for(Path path : result){
            System.out.println(path.toString(true));
        }*/
    }
}
