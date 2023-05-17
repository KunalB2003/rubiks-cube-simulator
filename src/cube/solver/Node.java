package src.cube.solver;

import src.cube.Move;
import src.cube.Cube;

public class Node implements Comparable<Node>{

    public Cube cube;
    protected int costToReach;
    protected int estimatedCostToGoal;
    protected Node parent;
    protected Move lastMove;

    public Node(Cube initial, Cube goal) {
        this(null, null, goal);
        this.cube = initial;
        this.estimatedCostToGoal = cube.compareTo(goal);
    }

    public Node(Node parent, Move lastMove, Cube goal) {
        if (lastMove != null) {
            this.cube = parent.cube.move(lastMove);
            this.costToReach = parent.costToReach + 1;
            this.estimatedCostToGoal = cube.compareTo(goal);
        }
        this.lastMove = lastMove;
        this.parent = parent;
    }

    public int getTotalCost() {
        return costToReach + estimatedCostToGoal;
    }
    
    @Override
    public boolean equals(Object otherNode) {
        return cube.equals(((Node)otherNode).cube);
    }

    @Override
    public int hashCode() {
        return cube.hashCode();
    }

    @Override
    public int compareTo(Node o) {
        return getTotalCost() - o.getTotalCost();
    }

}
