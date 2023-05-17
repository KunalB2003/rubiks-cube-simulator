package src.cube.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

import src.cube.Cube;
import src.cube.Move;

public class Solver {

    //private int[][] initialState, endState;
    private Cube initialCube, endCube;

    private Move[] moveList;

    public Solver(Cube initial, Cube end) {
        this.initialCube = initial;
        this.endCube = end;
        this.moveList = Move.allMoves();
    }

    public List<Move> solveCube() {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        Set<Node> visited = new HashSet<Node>();

        queue.add(new Node(initialCube, endCube));

        int counter = 0;
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            visited.add(currentNode);

            int curF = currentNode.costToReach;
            int curG = currentNode.estimatedCostToGoal;

            if (currentNode.cube.equals(endCube)) {
                System.out.println("sol found ("+counter+" nodes evaluated)");
                Stack<Node> stack = new Stack<Node>();
                Stream
                    .iterate(currentNode, n -> n.parent != null, n -> n.parent)
                    .forEach(stack::add);
                List<Move> moves = new ArrayList<Move>();
                while (!stack.empty()) {
                    Node n = stack.peek();
                    System.out.println(n.lastMove+", "+n.costToReach+", "+n.estimatedCostToGoal+", t:"+n.getTotalCost());
                    moves.add(stack.pop().lastMove);
                }
                //moves.forEach(m -> System.out.print(m + " "));
                return moves;
            }
            for (int i = 0; i < moveList.length; i++) {
                Move move = moveList[i];

                Node queueEntry = new Node(currentNode, move, endCube);

                if (!queue.contains(queueEntry) && !visited.contains(queueEntry)) {
                    queue.add(queueEntry);
                }

            }

            counter++;
            if (counter % 100 == 0) {
                System.out.println(counter + " " + queue.size() + " " + curF + " " + curG);
            }
        }
        return new ArrayList<Move>();
    }

}
