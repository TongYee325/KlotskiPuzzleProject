package controller;


import java.util.*;

import static controller.BlockType.*;


public class AiController {
    private final static int ROWS = 5;
    private final static int COLS = 4;

    static class State implements Comparable<State> {
        private final int g;//实际移动步数
        private final int h;//启发式估计值
        private final int f;//总代价
        private final int [][]map;
        List<String> path;//移动路径

        Map<BlockType, List<Position>> blocks;
        int moves;
        int priority;


        public State(int [][]map,int g,int h ,List<String> path) {
            this.map= map;
            this.g = g;
            this.h =h;
            this.f =g+h;
            this.path=path;
            blocks=parseMap(map);
        }


        @Override
        public int compareTo(State another) {//通过价值选取最小的步数
            if(this.f != another.f){
                return Integer.compare(this.f, another.f);
            }else{
                return Integer.compare(this.h, another.h);
            }
        }

        //Getter
        public int getG() { return g; }
        public int getH() { return h; }
        public int getF() { return f; }
        public int[][] getMap() { return map; }
        public List<String> getPath() { return path; }

        // 优化后的状态键生成方法
        String getStateKey() {
            StringBuilder sb = new StringBuilder();
            // 曹操位置最重要，优先记录
            sb.append(CAO_CAO.code).append(":")
                    .append(blocks.get(CAO_CAO).getFirst().x).append(",")
                    .append(blocks.get(CAO_CAO).getFirst().y).append("|");

            // 其他方块按类型排序后记录
            blocks.entrySet().stream()
                    .filter(e -> e.getKey() != CAO_CAO)
                    .sorted(Comparator.comparingInt(e -> e.getKey().code))
                    .forEach(e -> { /* 添加位置信息 */ });

            return sb.toString();
        }
    }

    public static Map<BlockType, List<Position>> parseMap(int[][] map) {
        Map<BlockType, List<Position>> blocks = new HashMap<>();
        blocks.put(EXIT, new ArrayList<>());
        blocks.put(SOLDIER, new ArrayList<>());
        blocks.put(GUAN_YU, new ArrayList<>());
        blocks.put(ZHANG_FEI, new ArrayList<>());
        blocks.put(ZHAO_YUN, new ArrayList<>());
        blocks.put(MA_CHAO, new ArrayList<>());
        blocks.put(HUANG_ZHONG, new ArrayList<>());
        blocks.put(CAO_CAO, new ArrayList<>());
        int [][]temp = new int[map.length][map[0].length];
        for(int i =0;i<map.length;i++) {
            System.arraycopy(map[i], 0, temp[i], 0, map[i].length);
        }
        boolean[][] visited = new boolean[map.length][map[0].length];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                // 跳过已处理或空单元格
                if (visited[i][j] ) continue;


                // 获取方块类型和尺寸
                BlockType type = BlockType.fromCode(temp[i][j]);
                if(type == null)continue;
                if(temp[i][j] == 0||temp[i][j] == 1){
                    visited[i][j] = true;
                    blocks.get(type).add(new Position(i, j));
                }
                else {
                    blocks.get(type).add(new Position(i, j));
                    if(temp[i][j]>=3&&temp[i][j]<=6){
                        visited[i][j] = true;
                        visited[i+1][j] =true;
                    }else if(temp[i][j]>=7){
                        visited[i][j] = true;
                        visited[i+1][j] =true;
                        visited[i+1][j+1] =true;
                        visited[i][j+1] =true;
                    }else if(temp[i][j]==2){
                        visited[i][j] = true;
                        visited[i][j+1] =true;
                    }else {
                        visited[i][j] = true;
                    }
                }
                // 边界检查
                if (i + type.getHeight() > temp.length || j + type.getWidth() >temp[i].length) {
                    System.err.println("无效方块位置: (" + i + "," + j + ")");
                    continue;
                }

            }
        }
        return blocks;
    }

    public List<String> solve(int[][]map) {
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        //初始化
        State startState = new State(map,0,0,new ArrayList<>());//todo
        openSet.add(startState);

        while(!openSet.isEmpty()) {
            State current = openSet.poll();
            if(isSolved(current)){
                /*return current.path;*/
                //print the path
                return current.path;

            }

            for(State neighbor :generateMoves(current)){
                String stateKey = neighbor.getStateKey();
                if(!visited.contains(stateKey)){
                    visited.add(stateKey);
                    openSet.add(neighbor);
                }
            }
        }
        return Collections.emptyList();//无解情况
    }

    private List<State>generateMoves(State current) {
        List<State> nextStates = new ArrayList<>();
        int[][] currentMap = deepCopyMap(current.getMap());
        // 遍历地图每个坐标
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int blockCode = currentMap[row][col];

                // 跳过空位和已处理方块（用0标记已处理）
                if (blockCode == 0) continue;

                // 获取方块类型和尺寸
                BlockType type = BlockType.fromCode(blockCode);
                int width =0;
                int height = 0;
                if (type != null) {
                    width = type.getWidth();
                    height = type.getHeight();
                }


                // 标记已处理区域（防止重复处理）
                markProcessed(currentMap, row, col, width, height);

                // 检查四个移动方向
                //0右1左2上3下
                checkMove(current, row, col, type, 0, nextStates,currentMap);
                checkMove(current, row, col, type, 1, nextStates,currentMap);
                checkMove(current, row, col, type,2, nextStates,currentMap);
                checkMove(current, row, col, type, 3, nextStates,currentMap);
            }
        }
        return nextStates;
    }

    private void markProcessed(int[][] map, int row, int col, int width, int height) {//已处理部分用-1表示
        /*for (int r = row; r < row + height; r++) {
            for (int c = col; c < col + width; c++) {
                if (isValidPosition(r, c)) {
                    map[r][c] = -1;
                }
            }
        }*/
    }

    private int[][] deepCopyMap(int[][] map) {
        int [][] newMap = new int[map.length][map[0].length];
        for(int i = 0; i < map.length; i++){
            System.arraycopy(map[i], 0, newMap[i], 0, map[0].length);
        }
        return newMap;
    }

    // 核心移动检测方法
    private void checkMove(State current, int row, int col, BlockType type,
                           int dir, List<State> nextStates, int[][] map) {
        if(type == null)return;
        int dx=0;
        int dy=0;
        switch (dir) {
            case 0: dx = 1; break;
            case 1: dx = -1; break;
            case 2: dy = -1; break;
            case 3: dy = 1; break;
            default: break;
        }
        //block的左上角
        int newRow = row + dy;
        int newCol = col + dx;

        int width = type.getWidth();
        int height = type.getHeight();

        //block的右下角
        int endRow = newRow + height - 1;
        int endCol = newCol + width - 1;

        // 基础边界检查
        if (!canMoveBasic(newRow, newCol,endRow,endCol)) return;

        // 碰撞检测
        if (!isMoveAreaEmpty(newRow,newCol,endRow,endCol,map)) return;

        // 创建新状态
        int[][] newMap = createNewMap(map, row, col, type, newRow, newCol);
        current.path.add(getMoveDescription(type,dir));
        int newG = current.getG() + 1;
        int newH = heuristic(newMap);
        nextStates.add(new State(newMap, newG, newH, current.path));
    }


    private String getMoveDescription(BlockType type, int dir) {
        return String.format("%s-%s;", type.name(), dir);//0123
    }

    // 创建移动后的新地图
    private int[][] createNewMap(int[][] original, int oldRow, int oldCol,
                                 BlockType type, int newRow, int newCol) {
        int[][] newMap = deepCopyMap(original);
        int code = type.getCode();
        int width = type.getWidth();
        int height = type.getHeight();

        // 清除原位置
        for (int r = oldRow; r < oldRow + height; r++) {
            for (int c = oldCol; c < oldCol + width; c++) {
                if (isValidPosition(r, c)) {
                    newMap[r][c] = 0;
                }
            }
        }

        // 设置新位置
        for (int r = newRow; r < newRow + height; r++) {
            for (int c = newCol; c < newCol + width; c++) {
                if (isValidPosition(r, c)) {
                    newMap[r][c] = code;
                }
            }
        }
        return newMap;
    }

    private boolean isMoveAreaEmpty(int newRow, int newCol, int endRow, int endCol,int[][] map) {
        // 检查目标区域是否全为0
        for (int r = newRow; r <= endRow; r++) {
            for (int c = newCol; c <= endCol; c++) {
                if (!isValidPosition(r, c) || map[r][c] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // 基础移动可能性判断
    private boolean canMoveBasic(int newRow, int newCol,int endRow, int endCol) {
        return newRow >= 0 && endRow < ROWS &&
                newCol >= 0 && endCol < COLS;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }



    private boolean isSolved(State state) {
        Position caoPosition = state.blocks.get(CAO_CAO).getFirst();
        return caoPosition.x ==3 && caoPosition.y ==1;
    }

    private int heuristic(int [][]map) {//估价函数
        int caoX=0;
        int caoY=0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] == 7){
                    caoX=i;
                    caoY=j;
                    break;
                }
            }
        }
        return Math.abs(caoX - 3)+Math.abs(caoY - 1);//曹操方块距离终点的曼哈顿距离作为估价值
    }




    static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
