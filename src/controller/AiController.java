package controller;

import org.jetbrains.annotations.NotNull;

import javax.swing.text.Position;
import java.util.*;

public class AiController {

    static class State implements Comparable<State> {
        Map<BlockType, List<Position>> blocks;
        int moves;
        int priority;
        int [][] statePanelMatrix;
        List<String> path;

        public State(int [][]map) {
            this.statePanelMatrix = map;
        }

        @Override
        public int compareTo(@NotNull State o) {
            //todo
            return 0;
        }


    }




    public List<String> solve(int[][]map) {
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        //初始化
        State startState = new State(map);
        openSet.add(startState);

        while(!openSet.isEmpty()) {
            State current = openSet.poll();
            if(isSolved(current)){
                return current.path;
            }

            for(State neighbor :generateMoves(current)){
                String stateKey = neighbor.getStateKey();
                //todo
                if(!visited.contains(stateKey)){
                    visited.add(stateKey);
                    openSet.add(neighbor);
                }
            }
            return Collections.emptyList();//无解情况
        }
    }

    private List<State>generateMoves(State current) {

    }

    private boolean isSolved(State state) {
        Position caoPosition = state.blocks.get(BlockType.CAO_CAO).getFirst();
        return caoPosition.x ==3 && caoPosition.y ==1;
    }



    static class Position {
        int x,y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
