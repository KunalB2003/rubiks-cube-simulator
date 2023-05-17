package src.cube;

import java.util.HashSet;
import java.util.PriorityQueue;

import src.cube.solver.Node;

public class Hashtest {
    public static void main(String[] args) {
        PriorityQueue<Node> set = new PriorityQueue<Node>();
        Cube c1 = new Cube();
        Cube c2 = c1.move(new Move(Face.RIGHT, 1));
        Cube c3 = c2.move(new Move(Face.LEFT, 1));
        Node n1 = new Node(c3, new Cube());
        set.add(n1);

        Cube c4 = c1.move(new Move(Face.LEFT, 1));
        Cube c5 = c4.move(new Move(Face.RIGHT, 1));
        Node n2 = new Node(c5, new Cube());
        System.out.println(set.contains(n2));
        System.out.println(n1.equals(n2));
    }
}
