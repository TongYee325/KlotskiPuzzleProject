package level;

import frame.GameFrame;
import gamestate.MyGameState;

public class GameLevel extends LevelBase {
    private final int GameLevelWidth = 600;
    private final int GameLevelHeight = 600;
    private final String GameLevelText = "Game Level";
    private Map gameMap;
    public GameLevel(MyGameState gameState) {
        super(gameState);
        gameMap =  new Map();
        super.setrFrame(new GameFrame(this, GameLevelText, GameLevelWidth, GameLevelHeight));


    }

}
class Map{
    public int[][] getMapIndex() {
        return mapIndex;
    }

    private int [][]mapIndex= {
            {3,7,7,4},
            {3,7,7,4},
            {5,2,2,6},
            {5,1,1,6},
            {1,0,0,1},
    };
    private int mapCol=4;
    private int mapRow=5;
    public Map(){
        mapIndex=new int[mapRow][mapCol];
    }

    public Map (int[][] designedMap){
        mapIndex=designedMap;
    }

    public Map(int columns, int rows) {
        mapIndex = new int[columns][rows];
        this.mapCol = columns;
        this.mapRow = rows;
    }
}