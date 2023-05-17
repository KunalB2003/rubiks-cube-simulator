package src.cube;

import java.util.HashSet;

import src.cube.solver.Node;

public class Hashtest {
    public static void main(String[] args) {
        HashSet<Node> set = new HashSet<Node>();
        // Cube c1 = new Cube();
        // Cube c2 = c1.move(new Move(Face.RIGHT, 1));
        // Cube c3 = c2.move(new Move(Face.LEFT, 1));
        // Node n1 = new Node(c3, new Cube());
        // set.add(n1);

        // Cube c4 = c1.move(new Move(Face.LEFT, 1));
        // Cube c5 = c4.move(new Move(Face.RIGHT, 1));
        // Node n2 = new Node(c5, new Cube());
        // System.out.println(set.contains(n2));
        // System.out.println(n1.equals(n2));
        Node n1 = new Node(new Cube().move(new Move(Face.RIGHT, 1)).move(new Move(Face.LEFT, 1)), new Cube());
        Node n2 = new Node(new Cube().move(new Move(Face.LEFT, 1)).move(new Move(Face.RIGHT, 1)), new Cube());

        System.out.println(n1.cube);
        System.out.println(n2.cube);
        System.out.println(n1.hashCode());
        System.out.println(n2.hashCode());
        System.out.println(n1.cube.bigHash);
        System.out.println(n2.cube.bigHash);
        System.out.println(n1.equals(n2));
    }

}

class Broga {
    public static void main(String[] args) {
        Cube c1 = new Cube();
        System.out.println(c1.bigHash);
        Cube c2 = c1.move(new Move(Face.RIGHT, 1));
        System.out.println(c2.bigHash);
        System.out.println(c1.bigHash.subtract(c2.bigHash));
        System.out.println(c2);
    }
}