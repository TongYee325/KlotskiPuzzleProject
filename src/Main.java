import controller.*;
import gamemode.*;
import gamestate.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyGameState myGameState = new MyGameState();
            KGameMode gameMode = new KGameMode();
            MyGameController gameController = new MyGameController();
            myGameState.startLevel(0);
        });
    }
}
