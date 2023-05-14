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
    private static final int[][] shuffleData = {
            { 1, 2, 4, 3, 1, 2, 3, 0 },
            { 2, 0, 3, 5, 3, 3, 3, 1 },
            { 0, 1, 5, 4, 0, 0, 0, 0 },
            { 0, 1, 5, 4, 2, 2, 2, 2 },
            { 0, 2, 5, 4, 1, 1, 3, 1 },
            { 1, 3, 4, 2, 3, 2, 1, 0 }
    };
    private int[][] states;

    public Cube() {
        instantiateCube();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Cube c = new Cube();
        System.out.println(c);

        while (s.hasNextLine()) {
            String in = s.nextLine();
            if (in.equals("reset")) {
                c = new Cube();
                System.out.println(c);
                continue;
            }

            Face f = faceMap.get(in);
            System.out.println(in + ": " + f);
            if (f == null) {
                continue;
            }
            c.move(f, 1);
            System.out.println(c);
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
        }
    }

    private void rotateFace(Face f) {
        int temp1 = states[f.getVal()][6];
        int temp2 = states[f.getVal()][7];
        for (int i = 7; i > 1; i--) {
            states[f.getVal()][i] = states[f.getVal()][i - 2];
        }
        states[f.getVal()][0] = temp1;
        states[f.getVal()][1] = temp2;

        int fval = f.getVal();
        int[] data = shuffleData[fval];
        int t1 = states[data[3]][data[7] * 2];
        int t2 = states[data[3]][data[7] * 2 + 1];
        int t3 = states[data[3]][(data[7] * 2 + 2) % 8];
        for (int i = 3; i > 0; i--) {
            states[data[i]][data[4 + i] * 2] = states[data[i - 1]][data[4 + i - 1] * 2];
            states[data[i]][data[4 + i] * 2 + 1] = states[data[i - 1]][data[4 + i - 1] * 2 + 1];
            states[data[i]][(data[4 + i] * 2 + 2) % 8] = states[data[i - 1]][(data[4 + i - 1] * 2 + 2) % 8];
        }
        states[data[0]][data[4] * 2] = t1;
        states[data[0]][data[4] * 2 + 1] = t2;
        states[data[0]][(data[4] * 2 + 2) % 8] = t3;
    }

    /**
     *    222
     *    222
     *    222
     * 111000444555
     * 111000444555
     * 111000444555
     *    333
     *    333
     *    333
     *  
     */
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
        for (int i = 0; i < builders.length; i++) {
            out.append(builders[i]);
            out.append('\n');
            builders[i] = new StringBuilder();
        }
    }

    private void printEmpty(StringBuilder[] builders) {
        builders[0].append("   ");
        builders[1].append("   ");
        builders[2].append("   ");
    }

    private void printFace(StringBuilder[] builders, int face) {
        IntStream.rangeClosed(0, 2).forEach((i) -> {
            builders[0].append(states[face][i]);
            builders[2].append(states[face][6 - i]);
        });
        builders[1].append("" + states[face][7] + face + states[face][3]);
    }
}
