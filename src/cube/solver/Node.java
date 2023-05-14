package src.cube.solver;

import java.util.Arrays;

import src.cube.Move;

public class Node {

    protected int[][] state;
    protected int costToReach;
    protected int estimatedCostToGoal;
    protected Node parent;
    protected Move lastMove;

    public Node(int[][] state, int costToReach, int estimatedCostToGoal, Node parent, Move lastMove) {
        this.state = state;
        this.costToReach = costToReach;
        this.estimatedCostToGoal = estimatedCostToGoal;
        this.parent = parent;
        this.lastMove = lastMove;
    }

    public int getTotalCost() {
        return costToReach + estimatedCostToGoal;
    }
    
    @Override
    public boolean equals(Object otherNode) {
        return Arrays.deepEquals(state, ((Node)otherNode).state);
    }

}
