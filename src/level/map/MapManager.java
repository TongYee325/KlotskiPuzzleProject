package level.map;

public class MapManager {
    private static final int[][] map0 = {
            {3, 7, 7, 4},
            {3, 7, 7, 4},
            {5, 2, 2, 6},
            {5, 0, 0, 6},
            {1, 1, 1, 1},
    };

    private static final int[][] map1 = {
            {0, 7, 7, 0},
            {3, 7, 7, 4},
            {3, 5, 6, 4},
            {1, 5, 6, 1},
            {2, 2, 1, 1},
    };

    private static final int[][] map2 = {
            {3, 7, 7, 1},
            {3, 7, 7, 1},
            {5, 6, 4, 1},
            {5, 6, 4, 1},
            {0, 2, 2, 0},
    };


    public static int[][] map(int index) {
        return switch (index) {
            case 1 -> map1;
            case 2 -> map2;
            default -> map0;
        };
    }

}
