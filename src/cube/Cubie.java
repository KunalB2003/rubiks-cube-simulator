package src.cube;

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

    private static int[][][] edgeMap = new int[EDGES.length][EDGES.length][EDGES[0].length];
    private static int[][][] cornerMap = new int[CORNERS.length][CORNERS.length][CORNERS[0].length];
    private static boolean initialized = false;
    
    private static int[][] getBaseState() {
        int[][] base = new int[6][8];
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[i].length; j++) {
                base[i][j] = 9;
            }
        }
        return base;
    }

    public static int[][][] getMovecountMap(int[][][] cubieSet) {
        int[][][] movecountMap = new int[cubieSet.length][cubieSet.length][cubieSet[0].length];
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
                    Solver solver = new Solver(initialCube, endCube);
                    movecountMap[start][end][rot] = solver.solveCube().size();
                }
            }
        }
        return movecountMap;
    }

    public static void initialize() {
        int[][][] temp = getMovecountMap(CORNERS);
        edgeMap = getMovecountMap(EDGES);
        cornerMap = temp;
        initialized = true;
    }
}
