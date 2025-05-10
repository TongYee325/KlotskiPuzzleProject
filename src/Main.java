import controller.*;
import gamemode.*;
import gamestate.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //KGameMode gameMode = new KGameMode();
            MyGameState myGameState = new MyGameState();
            myGameState.startLevel(0);
        });
        /*int  [][]map ={
                {3,7,7,4},
                {3,7,7,4},
                {5,2,2,6},
                {5,0,0,6},
                {1,1,1,1}
        };
        AiController controller = new AiController();
        修改
        System.out.println(controller.solve(map));
        */
    }
}
