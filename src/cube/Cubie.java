package src.cube;

import java.util.Arrays;
import java.util.stream.Collectors;

import src.cube.solver.Solver;

public class Cubie {
    public static final int[][][] CORNERS = {
            { { 0, 0 }, { 2, 6 }, { 1, 2 } },
            { { 0, 2 }, { 4, 0 }, { 2, 4 } },
            { { 0, 4 }, { 3, 2 }, { 4, 6 } },
            { { 0, 6 }, { 1, 4 }, { 3, 0 } },
            { { 5, 0 }, { 2, 2 }, { 4, 2 } },
            { { 5, 2 }, { 1, 0 }, { 2, 0 } },
            { { 5, 4 }, { 3, 6 }, { 1, 6 } },
            { { 5, 6 }, { 4, 4 }, { 3, 4 } },
    };
    public static final int[][][] EDGES = {
            { { 0, 1 }, { 2, 5 } },
            { { 0, 3 }, { 4, 7 } },
            { { 0, 5 }, { 3, 1 } },
            { { 0, 7 }, { 1, 3 } },
            { { 1, 1 }, { 2, 7 } },
            { { 2, 3 }, { 4, 1 } },
            { { 4, 5 }, { 3, 3 } },
            { { 3, 7 }, { 1, 5 } },
            { { 5, 1 }, { 2, 1 } },
            { { 5, 3 }, { 1, 7 } },
            { { 5, 5 }, { 3, 5 } },
            { { 5, 7 }, { 4, 3 } },
    };
    private static int[][] getBaseState() {
        int[][] base = new int[6][8];
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                base[i][j] = 9;
            }
        }
        return base;
    }

    public static void main(String[] args) {
        // int start = 3;
        // int end = 3;
        // int rot = 1;
        int[][][] cubieSet = EDGES;
        
        int[][][] movesMap = new int[cubieSet.length][cubieSet.length][cubieSet[0].length];
        for (int start = 0; start < cubieSet.length; start++) {
            for (int end = 0; end < cubieSet.length; end++) {
                for (int rot = 0; rot < cubieSet[0].length; rot++) {
                    int[][] initialState = getBaseState();
                    int[][] startCubie = cubieSet[start];
                    int[][] endState = getBaseState();
                    int[][] endCubie = cubieSet[end];
                    for (int i = 0; i < startCubie.length; i++) {
                        int[] pos = startCubie[i];
                        initialState[pos[0]][pos[1]] = i;
                        int[] pos2 = endCubie[(i+rot)%startCubie.length];
                        endState[pos2[0]][pos2[1]] = i;
                    }
                    Cube initialCube = new Cube(initialState);
                    Cube endCube = new Cube(endState);
                    // System.out.println("initial");
                    // System.out.println(initialCube);
                    // System.out.println("end");
                    // System.out.println(endCube);
                    Solver solver = new Solver(initialCube, endCube);
                    //System.out.println(solver.solveCube().size());
                    movesMap[start][end][rot] = solver.solveCube().size();
                }
            }
        }


        System.out.println();
        System.out.print("{");
        for (int i = 0; i < movesMap.length; i++) {
            System.out.print("{");
            for (int j = 0; j < movesMap.length; j++) {
                System.out.print("{");
                // for (int k = 0; k < movesMap[0][0].length; k++) {
                //     System.out.print(movesMap[i][j][k] + ", ");
                // }
                System.out.print(Arrays.stream(movesMap[i][j]).boxed().map(n -> n+"").collect(Collectors.joining(", ")));
                System.out.print("},");
            }
            System.out.print("},\n");
        }
        System.out.print("}");
    }
}
