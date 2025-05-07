package level.map;

public class GameMap{

    public int[][] getMapIndex() {
        return mapIndex;
    }
/*
    经典布局
    {3,7,7,4},
    {3,7,7,4},
    {5,2,2,6},
    {5,0,0,6},
    {1,1,1,1},
*/
    private int [][]mapIndex;
    //可在这里修改地图

    public int getMapCol() {
        return mapCol;
    }

    public int getMapRow() {
        return mapRow;
    }

    private int mapCol=4;
    private int mapRow=5;
    public GameMap(){
        this.mapIndex = MapManager.map(0);
    }

    public GameMap(int gameMapIndex){
        this.mapIndex = MapManager.map(gameMapIndex);
    }

    public GameMap (int[][] designedMap){
        mapIndex=designedMap;
    }

    public GameMap(int columns, int rows) {
        mapIndex = new int[columns][rows];
        this.mapCol = columns;
        this.mapRow = rows;
    }

    public int getMapID(int x , int y){
        return mapIndex[x][y];
    }
}
