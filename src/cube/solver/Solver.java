package src.cube.solver;

import java.util.ArrayList;
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

        while(!queue.isEmpty()) {
            
        }

        return null;
    }
    // solve algorithm (list<Move> return)
    // create priority queue
    // sort by total cost
    // heuristic cost + moves to get there
    // add start state of cube to queue
    // while queue isn't empty
    // take queue#poll value
    // check if value deep= end state in which case return
    // for each value from successor states
    // create a new node
    // add node to queue
    // if while loop ends
    // return null

}
