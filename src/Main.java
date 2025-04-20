import controller.*;
import gamemode.*;
import gamestate.*;

public class Main {
    public static void main(String[] args) {
        MyGameState myGameState = new MyGameState();
        KGameMode gameMode = new KGameMode();
        MyGameController gameController = new MyGameController();
        myGameState.startLevel(0);


    }
}
