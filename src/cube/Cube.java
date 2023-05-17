package src.cube;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import src.cube.solver.Solver;

public class Cube implements Comparable<Cube> {

    private static final int[][][] shuffleData = {
            { { 1, 2, 4, 3 }, { 1, 2, 3, 0 } },
            { { 2, 0, 3, 5 }, { 3, 3, 3, 1 } },
            { { 0, 1, 5, 4 }, { 0, 0, 0, 0 } },
            { { 1, 0, 4, 5 }, { 2, 2, 2, 2 } },
            { { 0, 2, 5, 3 }, { 1, 1, 3, 1 } },
            { { 1, 3, 4, 2 }, { 3, 2, 1, 0 } }
    };

    int[][] states;

    BigInteger bigHash;

    private static final int[][] DEFAULT_CUBE = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1 },
            { 2, 2, 2, 2, 2, 2, 2, 2 },
            { 3, 3, 3, 3, 3, 3, 3, 3 },
            { 4, 4, 4, 4, 4, 4, 4, 4 },
            { 5, 5, 5, 5, 5, 5, 5, 5 }
    };

    public Cube() {
        this(DEFAULT_CUBE);
    }

    public Cube(int[][] states) {

        this.states = new int[6][8];
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states[i].length; j++) {
                this.states[i][j] = states[i][j];
            }
        }
        calcHash();
    }

    private Cube getCopy() {
        return new Cube(states);
    }

    private void calcHash() {
        BigInteger temp = new BigInteger("0");
        for (int i = 0; i < this.states.length; i++) {
            for (int j = 0; j < this.states[0].length; j++) {
                temp = temp.shiftLeft(3);
                // temp = temp.multiply(new BigInteger("6"));
                temp = temp.add(new BigInteger("" + (char) (this.states[i][j] + 48)));

            }
        }
        this.bigHash = temp;
    }

    // Chat based cube interface
    public static void main(String[] args) {
        Cubie.initialize();

        Scanner s = new Scanner(System.in);
        Cube c = new Cube();
        System.out.println(c);

        while (s.hasNextLine()) {
            String in = s.nextLine().trim();
            if (in.equals("reset")) {
                c = new Cube();
                System.out.println(c);
                continue;
            } else if (in.contains("scramble")) {
                c = new Cube();
                int moves = Integer.parseInt(in.substring(in.indexOf(' ') + 1));
                Scramble scramble = new Scramble(moves);
                System.out.println(scramble);
                c = scramble.getCube();
                System.out.println(c);
                continue;
            } else if (in.equals("solve")) {
                Solver solver = new Solver(c, new Cube());
                List<Move> moves = solver.solveCube();
                System.out.println(moves.stream().map(Move::toString).collect(Collectors.joining(" ")));
                c = new Cube();
                continue;
            } else if (in.contains("simulate")) {
                int numRuns = 50;
                int[] vals = new int[numRuns];

                for (int i = 0; i < numRuns; i++) {

                    c = new Cube();
                    int moves = Integer.parseInt(in.substring(in.indexOf(' ') + 1));
                    Scramble scramble = new Scramble(moves);
                    c = scramble.getCube();

                    Solver solver = new Solver(c, new Cube());
                    List<Move> moves1 = solver.solveCube();
                    c = new Cube();

                    System.out.println("Cube " + (i + 1) + " solved");

                    vals[i] = solver.counter;

                }

                System.out.println("Average: " + Arrays.stream(vals).average().orElse(0));
                System.out.println("Median: " + Arrays.stream(vals).sorted().toArray()[numRuns / 2]);

                continue;
            }

            Face f = Face.strMap.get(Character.toString(in.toUpperCase().charAt(0)));
            System.out.println(in + ": " + f);
            if (f == null) {
                continue;
            }
            c = c.move(new Move(f, 1));
            System.out.println(c);
            System.out.println(c.compareTo(new Cube()));
            System.out.println("hash: " + c.bigHash);
        }
        s.close();
    }

    public Cube move(Move m) { // 1=>cwise 2=>double 3=ccwise
        Cube out = getCopy();
        int fval = m.face.getVal();
        int num = m.numMoves;
        for (int j = 0; j < 8; j++) {
            out.states[fval][(j + 2 * num) % 8] = states[fval][j];
        }
        int[] faceData = shuffleData[fval][0];
        int[] sideData = shuffleData[fval][1];
        for (int j = 0; j < 4; j++) {
            int j2 = (j + num) % 4;
            out.states[faceData[j2]][sideData[j2] * 2] = states[faceData[j]][sideData[j] * 2];
            out.states[faceData[j2]][sideData[j2] * 2 + 1] = states[faceData[j]][sideData[j] * 2 + 1];
            out.states[faceData[j2]][(sideData[j2] * 2 + 2) % 8] = states[faceData[j]][(sideData[j] * 2 + 2) % 8];
        }
        out.calcHash();
        return out;
    }

    @Override
    public int compareTo(Cube o) {
        // int incorrectPositions = 0;
        // for (int i = 0; i < 6; i++) {
        // for (int j = 0; j < 8; j++) {
        // if (this.states[i][j] != o.states[i][j]) {
        // incorrectPositions++;
        // }
        // }
        // }
        // return incorrectPositions;

        return Cubie.calculateHeuristic(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cube)) {
            return false;
        }
        // boolean temp = Arrays.deepEquals(states, ((Cube) o).states);
        // return temp;
        return this.bigHash.equals(((Cube) o).bigHash);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        StringBuilder[] builders = new StringBuilder[] {
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
        };

        printEmpty(builders, false, false);
        printFace(builders, 2, true);
        LineBreak(out, builders);
        printFace(builders, 1, false);
        printFace(builders, 0, false);
        printFace(builders, 4, false);
        printFace(builders, 5, true);
        LineBreak(out, builders);
        printEmpty(builders, true, true);
        printFace(builders, 3, true);
        printEmpty(builders, true, false);
        printEmpty(builders, true, false);
        LineBreak(out, builders);

        return out.toString() + "    *---*";
    }

    private void LineBreak(StringBuilder out, StringBuilder[] builders) {
        for (int i = 0; i < builders.length; i++) {
            out.append(builders[i]);
            out.append('\n');
            builders[i] = new StringBuilder();
        }
    }

    private void printEmpty(StringBuilder[] builders, boolean bottom, boolean anchorLeft) {
        builders[0].append(bottom ? (anchorLeft ? "*---" : "---*") : "    ");
        builders[1].append("    ");
        builders[2].append("    ");
        builders[3].append("    ");
    }

    private void printFace(StringBuilder[] builders, int face, boolean last) {
        builders[0].append("*---");
        for (int i = 1; i < builders.length; i++) {
            builders[i].append("|");
        }
        IntStream.rangeClosed(0, 2).forEach((i) -> {
            builders[1].append(states[face][i]);
            builders[3].append(states[face][6 - i]);
        });
        builders[2].append("" + states[face][7] + face + states[face][3]);
        if (last) {
            builders[0].append("*");
            for (int i = 1; i < builders.length; i++) {
                builders[i].append("|");
            }
        }
    }

    public int[][] getStates() {
        return states;
    }

    @Override
    public int hashCode() {
        return bigHash.intValue();
    }

}
