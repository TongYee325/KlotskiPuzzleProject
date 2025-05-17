package level.map;

public class MapManager {
    private static final int[][] map0 = {
            {3, 7, 7, 4},
            {3, 7, 7, 4},
            {5, 2, 2, 6},
            {5, 1, 1, 6},
            {1, 0, 0, 1},
    };

    private static final int[][] map1 = {
            {3, 7, 7, 4},
            {3, 7, 7, 4},
            {1, 2, 2, 1},
            {5, 1, 1, 6},
            {5, 0, 0, 6},
    };

    private static final int[][] map2 = {
            {0, 7, 7, 0},
            {3, 7, 7, 6},
            {3, 4, 5, 6},
            {1, 4, 5, 1},
            {2, 2, 1, 1},
    };

    private static final int[][] map3 = {
            {3, 7, 7, 4},
            {3, 7, 7, 4},
            {1, 1, 1, 1},
            {5, 2, 2, 6},
            {5, 0, 0, 6},
    };

    private static final int[][] map4 = {
            {1, 7, 7, 1},
            {3, 7, 7, 4},
            {3, 2, 2, 4},
            {5, 1, 1, 6},
            {5, 0, 0, 6},
    };

    private static final int[][] map5 = {
            {3, 7, 7, 1},
            {3, 7, 7, 1},
            {5, 2, 2, 4},
            {5, 6, 0, 4},
            {1, 6, 0, 1},
    };

    private static final int[][] map6 = {
            {1, 7, 7, 1},
            {1, 7, 7, 1},
            {3, 4, 5, 6},
            {3, 4, 5, 6},
            {0, 2, 2, 0},
    };

    private static final int[][] map7 = {
            {1, 7, 7, 1},
            {3, 7, 7, 4},
            {3, 5, 6, 4},
            {1, 5, 6, 1},
            {0, 2, 2, 0},
    };

    private static final int[][] map8 = {
            {6, 7, 7, 1},
            {6, 7, 7, 1},
            {5, 4, 3, 1},
            {5, 4, 3, 1},
            {0, 2, 2, 0},
    };

    public static int[][] map(int index) {
        return switch (index) {
            case 1 -> map1;
            case 2 -> map2;
            case 3 -> map3;
            case 4 -> map4;
            case 5 -> map5;
            case 6 -> map6;
            case 7 -> map7;
            case 8 -> map8;
            default -> map0;
        };
    }

}
