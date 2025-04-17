import controller.*;
import gamemode.*;
import gamestate.*;

public class Main {
    public static void main(String[] args) {
        MyGameState mygameState = new MyGameState();
        KGameMode gameMode = new KGameMode();
        MyGameController gameController = new MyGameController();
        mygameState.startLevel(0);

    }
}
