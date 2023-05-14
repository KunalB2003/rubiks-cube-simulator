package src.solver.cube;

import static src.solver.cube.Cube.Face.BACK;
import static src.solver.cube.Cube.Face.DOWN;
import static src.solver.cube.Cube.Face.FRONT;
import static src.solver.cube.Cube.Face.LEFT;
import static src.solver.cube.Cube.Face.RIGHT;
import static src.solver.cube.Cube.Face.UP;

import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Cube {

    public enum Face {
        DOWN(3), UP(2), FRONT(0), BACK(5), LEFT(1), RIGHT(4);

        private final int val;

        private Face(int val) {
            this.val = val;
        }

        public Face getFace(String in) {
            switch (in.toLowerCase()) {
                case "d":
                    return DOWN;
                case "u":
                    return UP;
                case "f":
                    return FRONT;
                case "b":
                    return BACK;
                case "l":
                    return LEFT;
                case "r":
                    return RIGHT;
                default:
                    return null;
            }
        }

        public int getVal() {
            return val;
        }
    }

    private static final Map<String, Face> faceMap = Map.of("F", FRONT, "B", BACK, "U", UP, "D", DOWN, "L", LEFT, "R",
            RIGHT);
    private static final int[][] shuffleData = { { 1, 2, 4, 3, 1, 2, 3, 0 }, {2, 0, 3, 5, 3, 3, 3, 1}, {0, 1, 5, 4, 0, 0, 0, 0}, {}};
    private int[][] states;

    public Cube() {
        instantiateCube();
        toString();
    }

    public static void main(String[] args) {
        System.out.println(new Cube());
        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {
            String in = s.nextLine();
            System.out.println(in + ": " + faceMap.get(in));
        }

        s.close();
    }

    private void instantiateCube() {
        states = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                states[i][j] = i;
            }
        }
    }

    public void move(Face f, int direction) { // 1=>cwise 2=>double 3=ccwise
        for (int i = 0; i < direction; i++) {
            rotateFace(f);
            switch (f) {
                case UP:
                    /**
                     * 
                     * 2 clockwise
                     * top row: 1 0 4 5 left
                     */
                    break;
                case DOWN:
                    /**
                     * 3 clockwise
                     * bottom row: 1 0 4 5 right
                     */
                    break;
                case LEFT:
                    /**
                     * 1 clockwise
                     * left column : 2 0 3 down
                     * right column 5 up
                     */
                    break;
                case RIGHT:
                    /**
                     * 4 clockwise
                     * right column: 3 0 2 up
                     * left column: 5 down
                     */
                    break;
                case FRONT:
                    /**
                     * 0 clockwise
                     * left column: 4 down
                     * right column: 1 up
                     * top row: 3 left
                     * bottom row: 2 right
                     */
                    break;
                case BACK:
                    /**
                     * 5 clockwise
                     * left column: 1 down
                     * right column: 4 up
                     * top row: 2 left
                     * bottom row: 3 right
                     */
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect face inputted.");
            }
        }
    }

    private void rotateFace(Face f) {
        int temp1 = states[f.getVal()][6];
        int temp2 = states[f.getVal()][7];
        for (int i = 2; i < 8; i++) {
            states[f.getVal()][i] = states[f.getVal()][i - 2];
        }
        states[f.getVal()][0] = temp1;
        states[f.getVal()][1] = temp2;
    }

    private void shuffle3(Face f) {
        int fval = f.getVal();
        int[] data = shuffleData[fval];
        int t1 = states[data[0]][data[4] * 2];
        int t2 = states[data[0]][data[4] * 2 + 1];
        int t3 = states[data[0]][(data[4] * 2 + 2)%8];
        for (int i = 1; i < 4; i++) {   
            states[data[i]][data[4+i]*2] = states[data[i-1]][data[4+i-1] * 2];
            states[data[i]][data[4+i]*2+1] = states[data[i-1]][data[4+i-1] * 2+1];
            states[data[i]][(data[4+i]*2+2)%8] = states[data[i-1]][(data[4+i-1] * 2+2)%8];
        }
        states[data[3]][data[7]*2] = t1;
        states[data[3]][data[7]*2+1] = t2;
        states[data[3]][(data[7]*2+2)%8] = t3;

    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        StringBuilder[] builders = new StringBuilder[] {
            new StringBuilder(),
            new StringBuilder(),
            new StringBuilder()
        };

        printEmpty(builders);
        printFace(builders, 2);

        tripleLineBreak(out, builders);

        printFace(builders, 1);
        printFace(builders, 0);
        printFace(builders, 4);
        printFace(builders, 5);

        tripleLineBreak(out, builders);

        printEmpty(builders);
        printFace(builders, 3);

        tripleLineBreak(out, builders);

        return out.toString();
    }

    private void tripleLineBreak(StringBuilder out, StringBuilder[] builders) {
        out.append(builders[0]);
        out.append('\n');
        out.append(builders[1]);
        out.append('\n');
        out.append(builders[2]);
        out.append('\n');
        builders[0] = new StringBuilder();
        builders[1] = new StringBuilder();
        builders[2] = new StringBuilder();
    }

    private void printEmpty(StringBuilder[] builders) {
        builders[0].append("   ");
        builders[1].append("   ");
        builders[2].append("   ");
    }

    private void printFace(StringBuilder[] builders, int face) {
        IntStream.rangeClosed(0, 2).forEach((i) -> {
            builders[1].append(states[face][i]);
            builders[3].append(states[face][7 - i]);
        });
        builders[2].append("" + states[face][7] + face + states[face][3]);
    }
}
