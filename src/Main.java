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
    }
}
