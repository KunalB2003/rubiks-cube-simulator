package src.cube.solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.stream.Stream;

import src.cube.Cube;
import src.cube.Move;

public class Solver {

    // private int[][] initialState, endState;
    private Cube initialCube, endCube;
    
    private Move[] moveList;
    public int counter;

    public Solver(Cube initial, Cube end) {
        this.initialCube = initial;
        this.endCube = end;
        this.moveList = Move.allMoves();
        this.counter = 0;
    }

    private class SearchBuffer {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        HashSet<Node> inactiveSet = new HashSet<Node>();
        Cube goal;

        public SearchBuffer(Cube goal) {
            this.goal = goal;
        }

        public synchronized void tryAdd(Node n) {
            if (!contains(n)) {
                queue.add(n);
                inactiveSet.add(n);
            }
        }

        public synchronized Node poll() {
            return queue.poll();
        }

        public synchronized boolean isEmpty() {
            return queue.isEmpty();
        }

        public synchronized boolean contains(Node n) {
            return inactiveSet.contains(n);
        }

        public synchronized Node getEquivalentNode(Node n) {
            for (Node e : inactiveSet) {
                if (e.equals(n)) {
                    return e;
                }
            }
            return null;
        }
    }

    public List<Move> solveCube() {
        SearchBuffer forwardsSearch = new SearchBuffer(endCube);
        forwardsSearch.tryAdd(new Node(initialCube, endCube));

        SearchBuffer backwardsSearch = new SearchBuffer(initialCube);
        backwardsSearch.tryAdd(new Node(endCube, initialCube));

        while (!forwardsSearch.isEmpty() && !backwardsSearch.isEmpty()) {
            SearchBuffer curSearch = (counter%2==0)?forwardsSearch:backwardsSearch;
            Node currentNode = curSearch.poll();

            int curF = currentNode.costToReach;
            int curG = currentNode.estimatedCostToGoal;

            if (currentNode.cube.equals(curSearch.goal)) {
                System.out.println("sol found (" + counter + " nodes evaluated)");
                Stack<Node> stack = new Stack<Node>();
                Stream
                        .iterate(currentNode, n -> n.parent != null, n -> n.parent)
                        .forEach(stack::add);
                List<Move> moves = new ArrayList<Move>();
                while (!stack.empty()) {
                    Node n = stack.peek();
                    System.out.println(n.lastMove + ", " + n.costToReach + ", " + n.estimatedCostToGoal + ", t:"
                            + n.getTotalCost());
                    moves.add(stack.pop().lastMove);
                }
                // moves.forEach(m -> System.out.print(m + " "));
                return moves;
            } else if (curSearch == forwardsSearch && backwardsSearch.contains(currentNode)) { // check for intersection
                System.out.println("Intersection found (" + counter + " nodes evaluated)");
                Stack<Node> stack = new Stack<Node>();
                Stream
                        .iterate(currentNode, n -> n.parent != null, n -> n.parent)
                        .forEach(stack::add);
                List<Move> moves = new ArrayList<Move>();
                while (!stack.empty()) {
                    Node n = stack.peek();
                    System.out.println(n.lastMove + ", " + n.costToReach + ", " + n.estimatedCostToGoal + ", t:"
                            + n.getTotalCost());
                    moves.add(stack.pop().lastMove);
                }
                Node backwardsNode = backwardsSearch.getEquivalentNode(currentNode);
                Stream
                        .iterate(backwardsNode, n -> n.parent != null, n -> n.parent)
                        .forEach(n -> moves.add(n.lastMove.getReverse()));
                return moves;
            }
            for (int i = 0; i < moveList.length; i++) {
                Move move = moveList[i];
                Node adjacentNode = new Node(currentNode, move, endCube);
                curSearch.tryAdd(adjacentNode);
            }

            counter++;
            if (counter % 10000 == 0) {
                System.out.println(counter + " " + curSearch.queue.size() + " " + (curF + curG) + " " + curF + " " + curG);
            }
        }
        System.out.println("solve failed");
        return new ArrayList<Move>();
    }

}
