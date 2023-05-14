package src.solver.cube;

import java.util.stream.IntStream;

public class Cube {

    public enum Face {
        DOWN(3), UP(2), FRONT(0), BACK(5), LEFT(1), RIGHT(4);
        private final int val ;
        private Face(int val) {
            this.val = val;
        }
        public int getVal() {
            return val;
        }

        public enum Side {
            TOP(0), BOTTOM(2), LEFT(3), RIGHT(1); 
            private final int val;
            private Side(int val) {
                this.val = val;
            }
            public int getVal() {
                return val;
            }
        }
    }
    private int[][] states;

    public Cube() {
        instantiateCube();
        toString();
    }

    public static void main(String[] args) {
        System.out.println(new Cube());

    }

    private void instantiateCube() {
        states = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                states[i][j] = i;
            }
        }
    }

    public void move(Face f, int direction) { // 1, 2, 3
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
                     * 3 clockwise
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
                    break;
                default:
                    throw new IllegalArgumentException("Incorrect face inputted.");
            }
        }
    }

    private void rotateFace(Face f) {
        int corner = states[f.getVal()][0];
        int edge = states[f.getVal()][1];
        for (int i = 0; i < 6; i++) {
            states[f.getVal()][i + 2] = states[f.getVal()][i];
        }

    }
    
    private void shuffle3() {
        🤡
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        printEmpty(sb1, sb2, sb3);
        printFace(sb1, sb2, sb3, 2);

        out.append(sb1);
        out.append('\n');
        out.append(sb2);
        out.append('\n');
        out.append(sb3);
        out.append('\n');
        sb1 = new StringBuilder();
        sb2 = new StringBuilder();
        sb3 = new StringBuilder();

        printFace(sb1, sb2, sb3, 1);
        printFace(sb1, sb2, sb3, 0);
        printFace(sb1, sb2, sb3, 4);
        printFace(sb1, sb2, sb3, 5);

        out.append(sb1);
        out.append('\n');
        out.append(sb2);
        out.append('\n');
        out.append(sb3);
        out.append('\n');
        sb1 = new StringBuilder();
        sb2 = new StringBuilder();
        sb3 = new StringBuilder();

        printEmpty(sb1, sb2, sb3);
        printFace(sb1, sb2, sb3, 3);

        out.append(sb1);
        out.append('\n');
        out.append(sb2);
        out.append('\n');
        out.append(sb3);
        out.append('\n');

        return out.toString();
    }

    private void printEmpty(StringBuilder sb1, StringBuilder sb2, StringBuilder sb3) {
        sb1.append("   ");
        sb2.append("   ");
        sb3.append("   ");
    }

    private void printFace(StringBuilder sb1, StringBuilder sb2, StringBuilder sb3, int face) {
        IntStream.rangeClosed(0, 2).forEach((i) -> {
            sb1.append(states[face][i]);
            sb3.append(states[face][7 - i]);
        });
        sb2.append("" + states[face][7] + face + states[face][3]);
    }
}
