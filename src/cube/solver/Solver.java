package src.cube.solver;

import java.util.ArrayList;
import java.util.HashSet;
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

    public Solver(Cube cube) {
        this.initialCube = cube;
        endCube = new Cube();
        this.moveList = Move.allMoves();
    }

    public ArrayList<Move> solveCube() {
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
                System.out.println("sol found");
                Stack<Node> stack = new Stack<Node>();
                Stream
                    .iterate(currentNode, n -> n.parent != null, n -> n.parent)
                    .forEach(stack::add);
                while (!stack.empty()) {
                    System.out.print(stack.pop().lastMove + " ");
                }
                return null;
            }
            for (int i = 0; i < moveList.length; i++) {
                Move move = moveList[i];

                Node queueEntry = new Node(currentNode, move, endCube);

                if (!queue.contains(queueEntry) && !visited.contains(queueEntry)) {
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
