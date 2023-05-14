package src.cube.solver;

import src.cube.Move;

public class Node {

    private int[][] state;
    private int costToReach;
    private int estimatedCostToGoal;
    private Node parent;
    private Move lastMove;

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

}
