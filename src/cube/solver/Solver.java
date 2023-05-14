package src.cube.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import src.cube.Cube;
import src.cube.Move;

public class Solver {

    private int[][] initialState, endState;
    private Move[] moveList;

    public Solver(Cube cube) {
        this.initialState = cube.getStates();
        this.endState = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                this.endState[i][j] = i;
            }
        }
        this.moveList = Move.allMoves();
    }

    private int heuristic(int[][] state) {
        int incorrectPositions = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] != endState[i][j]) {
                    incorrectPositions++;
                }
            }
        }
        return incorrectPositions;
    }

    private ArrayList<int[][]> getNextStates(int[][] state) {
        ArrayList<int[][]> nextStates = new ArrayList<int[][]>();

        for (Move m : moveList) {
            nextStates.add(Cube.nextStatesFromStates(state, m));
        }

        return nextStates;
    }

    public ArrayList<Move> solveCube() {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.getTotalCost() - n2.getTotalCost();
            }
        });

        queue.add(new Node(initialState, 0, heuristic(initialState), null, null));

        int counter = 0;
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            int curF = currentNode.costToReach;
            int curG = currentNode.estimatedCostToGoal;

            if (Arrays.deepEquals(currentNode.state, endState)) {
                System.out.println("sol found");
                return null;
            }
            for (int i = 0; i < moveList.length; i++) {
                int[][] nextStates = Cube.nextStatesFromStates(currentNode.state, moveList[i]);
                int costToReach = currentNode.costToReach + 1;
                int estimatedCostToGoal = heuristic(nextStates);
                Move move = moveList[i];

                Node queueEntry = new Node(nextStates, costToReach, estimatedCostToGoal, currentNode, move);

                if (!queue.contains(queueEntry)) {
                    queue.add(queueEntry);
                }

            }

            counter++;
            if (counter % 10 == 0) {
                System.out.println(counter + " " + queue.size() + " " + curF + " " + curG);
            }
        }
        return null;
    }

}
