import controller.*;
import gamemode.*;
import gamestate.*;

public class Main {
    public static void main(String[] args) {
        MyGameState gameState = new MyGameState();
        KGameMode gameMode = new KGameMode();
        MyGameController gameController = new MyGameController();
        gameState.startLevel(0);



    }
}
