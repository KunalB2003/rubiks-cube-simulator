package src.cube;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Cube {

    private static final int[][][] shuffleData = {
            { { 1, 2, 4, 3 }, { 1, 2, 3, 0 } },
            { { 2, 0, 3, 5 }, { 3, 3, 3, 1 } },
            { { 0, 1, 5, 4 }, { 0, 0, 0, 0 } },
            { { 1, 0, 4, 5 }, { 2, 2, 2, 2 } },
            { { 0, 2, 5, 3 }, { 1, 1, 3, 1 } },
            { { 1, 3, 4, 2 }, { 3, 2, 1, 0 } }
    };

    private int[][] states;

    public Cube() {
        createCube();
    }

    public Cube(int[][] states) {
        createCube(states);
    }

    // Chat based cube interface
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
            } else if (in.contains("scramble")) {
                c = new Cube();
                int moves = Integer.parseInt(in.substring(in.indexOf(' ') + 1));
                System.out.println(c.scramble(moves).stream().map(Move::toString).collect(Collectors.joining(" ")));
                System.out.println(c);
                continue;
            }

            Face f = Face.strMap.get(Character.toString(in.toUpperCase().charAt(0)));
            System.out.println(in + ": " + f);
            if (f == null) {
                continue;
            }
            c.move(new Move(f, 1));
            System.out.println(c);
        }
        s.close();
    }

    private void createCube() {
        this.states = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                this.states[i][j] = i;
            }
        }
    }

    private void createCube(int[][] states) {
        this.states = states;
    }

    public List<Move> scramble(int moves) {
        Random r = new Random();
        List<Move> out = IntStream
                .iterate(r.nextInt(6), t -> {
                    int j;
                    do {
                        j = r.nextInt(6);
                    } while (t == j);
                    return j;
                })
                .limit(moves)
                .boxed()
                .map(t -> new Move(Face.intMap.get(t), r.nextInt(3) + 1))
                .toList();
        out.forEach(this::move);
        return out;
    }

    public int[][] move(Move m) { // 1=>cwise 2=>double 3=ccwise
        for (int i = 0; i < m.num; i++) {
            rotateFace(m.face);
        }

        return states;
    }

    private int[][] rotateFace(Face f) {
        int temp1 = states[f.getVal()][6];
        int temp2 = states[f.getVal()][7];
        for (int i = 7; i > 1; i--) {
            states[f.getVal()][i] = states[f.getVal()][i - 2];
        }
        states[f.getVal()][0] = temp1;
        states[f.getVal()][1] = temp2;

        int fval = f.getVal();
        int[] faceData = shuffleData[fval][0];
        int[] sideData = shuffleData[fval][1];
        int t1 = states[faceData[3]][sideData[3] * 2];
        int t2 = states[faceData[3]][sideData[3] * 2 + 1];
        int t3 = states[faceData[3]][(sideData[3] * 2 + 2) % 8];
        for (int i = 3; i > 0; i--) {
            states[faceData[i]][sideData[i] * 2] = states[faceData[i - 1]][sideData[i - 1] * 2];
            states[faceData[i]][sideData[i] * 2 + 1] = states[faceData[i - 1]][sideData[i - 1] * 2 + 1];
            states[faceData[i]][(sideData[i] * 2 + 2) % 8] = states[faceData[i - 1]][(sideData[i - 1] * 2 + 2) % 8];
        }
        states[faceData[0]][sideData[0] * 2] = t1;
        states[faceData[0]][sideData[0] * 2 + 1] = t2;
        states[faceData[0]][(sideData[0] * 2 + 2) % 8] = t3;

        return states;
    }

    public static int[][] nextStatesFromStates(int[][] states, Move m) {
        int[][] nextState = new int[6][8];
        
        Cube c = new Cube(states);
        return c.move(m);
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
}
