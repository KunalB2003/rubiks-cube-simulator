package src;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import src.cube.Cube;
import src.cube.Cubie;
import src.cube.Face;
import src.cube.Move;
import src.cube.Scramble;
import src.cube.solver.Solver;

public class Text {

    private static Scanner sc = new Scanner(System.in);
    private static Cube cube = new Cube();
    private static Random rand = new Random();
    private static boolean running = true;

    public static void main(String[] args) {
        Map<String, Consumer<String[]>> commands = new HashMap<String, Consumer<String[]>>();

        commands.put("stop", (commandArgs) -> {
            running = false;
        });

        commands.put("reset", (commandArgs) -> {
            cube = new Cube();
            System.out.println(cube);
            rand = new Random();
        });
        commands.put("seed", (commandArgs) -> { // seed
            if (!argCheck(commandArgs, 1)) {
                return;
            }

            long val = Long.parseLong(commandArgs[0]);
            rand.setSeed(val);
            System.out.println("Seed set to " + val);
        });
        commands.put("scramble", (commandArgs) -> { // moves
            if (!argCheck(commandArgs, 1)) {
                return;
            }

            cube = new Cube();
            int moves = Integer.parseInt(commandArgs[0]);
            Scramble scramble = new Scramble(rand, moves);
            System.out.println(scramble);
            cube = scramble.getCube();
            System.out.println(cube);
        });
        commands.put("solve", (commandArgs) -> {
            Solver solver = new Solver(cube, new Cube(), 10);
            List<Move> moves = solver.solveCube(true);
            System.out.println(moves.stream().map(Move::toString).collect(Collectors.joining(" ")));
            cube = new Cube();
        });
        commands.put("simulate", (commandArgs) -> { // scramblemoves count seed
            if (!argCheck(commandArgs, 3)) {
                return;
            }

            int[] intArgs = getIntArgs(commandArgs);

            int[] vals = new int[intArgs[1]];
            rand.setSeed(Integer.parseInt(commandArgs[2]));

            for (int i = 0; i < intArgs[1]; i++) {
                System.out.println();

                int moves = intArgs[0];
                Scramble scramble = new Scramble(rand, moves);
                System.out.println("Scramble: " + scramble);
                cube = scramble.getCube();

                Solver solver = new Solver(cube, new Cube(), 10);
                List<Move> moves1 = solver.solveCube(true);
                System.out.println(
                        "Solution: " +
                                moves1.stream().map(Move::toString).collect(Collectors.joining(" ")));
                cube = new Cube();

                System.out.println("Cube " + (i + 1) + " solved");

                vals[i] = solver.counter;
            }

            System.out.println();
            System.out.println("Average: " + Arrays.stream(vals).average().orElse(0));
            System.out.println("Median: " +
                    Arrays.stream(vals).sorted().toArray()[intArgs[1] / 2]);

        });
        commands.put("display", (commandArgs) -> {
            System.out.println(cube);
        });

        Cubie.initialize();
        while (running && sc.hasNextLine()) {
            String[] lineInput = sc.nextLine().trim().split(" ");
            if (lineInput.length == 0) {
                continue;
            }

            Consumer<String[]> command = commands.get(lineInput[0]);

            if (command != null) {
                String[] commandArgs = null;
                if (lineInput.length > 1) {
                    commandArgs = new String[lineInput.length - 1];
                    for (int i = 0; i < lineInput.length - 1; i++) {
                        commandArgs[i] = lineInput[i + 1];
                    }
                } else {
                    commandArgs = new String[] {};
                }

                command.accept(commandArgs);
            } else {
                Face f = Face.strMap.get(Character.toString(lineInput[0].toUpperCase().charAt(0)));
                if (f == null) {
                    continue;
                }
                cube = cube.move(new Move(f, 1));
                System.out.println(cube);
                System.out.println(cube.compareTo(new Cube()));
            }
        }

        sc.close();

    }

    private static int[] getIntArgs(String[] args) {
        return Arrays.stream(args).map(s -> Integer.parseInt(s)).mapToInt(i -> i).toArray();
    }

    private static boolean argCheck(String[] args, int argCount) {
        if (args.length != argCount) {
            System.out.println("Incorrect Args.");
            return false;
        }
        return true;
    }

}
