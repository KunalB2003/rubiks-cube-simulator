package src.cube.solver;

import src.cube.Cube;

public class Solver {

    private int[][] initialState, endState;
    // end state

    public Solver(Cube cube) {
        this.initialState = cube.getStates();
        this.endState = new int[6][8];
        this.endState = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                this.endState[i][j] = i;
            }
        }
    }

    // calculate heuristic cost (int)
    // get number of misplaced cubelets
    // iterate through the cube and compare to endstate to see how many are
    // incorrectly placed

    // calculate the successor states from the current position (list<int[][]>
    // return)
    // get list of states and return them in a list

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
