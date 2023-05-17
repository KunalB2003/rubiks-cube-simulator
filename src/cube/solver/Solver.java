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

    private SearchBuffer forwardsSearch;
    private SearchBuffer backwardsSearch;

    public Solver(Cube initial, Cube end) {
        this.initialCube = initial;
        this.endCube = end;
        this.moveList = Move.allMoves();
        this.counter = 0;
    }

    private class SearchBuffer {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        HashSet<Node> inactiveSet = new HashSet<Node>();
        
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
        forwardsSearch = new SearchBuffer();
        forwardsSearch.tryAdd(new Node(initialCube, endCube));

        backwardsSearch = new SearchBuffer();
        backwardsSearch.tryAdd(new Node(endCube, initialCube));

        while (!forwardsSearch.isEmpty() && !backwardsSearch.isEmpty()) {
            SearchBuffer curSearch = (counter%2==0)?forwardsSearch:backwardsSearch;
            Node currentNode = curSearch.poll();

            int curF = currentNode.costToReach;
            int curG = currentNode.estimatedCostToGoal;

            for (int i = 0; i < moveList.length; i++) {
                Move move = moveList[i];
                Node adjacentNode = new Node(currentNode, move, endCube);

                if (curSearch == forwardsSearch && backwardsSearch.contains(adjacentNode) || curSearch == backwardsSearch && forwardsSearch.contains(adjacentNode) ) {
                    System.out.println("Intersection found (" + counter + " nodes evaluated)");

                    return getSolution(adjacentNode, curSearch);
                }

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

    private List<Move> getSolution(Node intersectionNode, SearchBuffer mainSearch) {
        boolean forwards = mainSearch == forwardsSearch;
        Stack<Node> stack = new Stack<Node>();
        List<Move> moves = new ArrayList<Move>();
        Stream
                .iterate(intersectionNode, n -> n.parent != null, n -> n.parent)
                .forEach(stack::add);
        while (!stack.empty()) {
            moves.add(stack.pop().lastMove);
        }
        Node oppositeNode = ((forwards)?backwardsSearch:forwardsSearch).getEquivalentNode(intersectionNode);
        Stream
                .iterate(oppositeNode, n -> n.parent != null, n -> n.parent)
                .forEach(n -> moves.add(n.lastMove.getReverse()));

        if (!forwards) {
            List<Move> temp = new ArrayList<Move>();
            for (int j = 0; j < moves.size(); j++) {
                temp.add(0, moves.get(j).getReverse());
            }
            return temp;
        }
        return moves;
    }

}
