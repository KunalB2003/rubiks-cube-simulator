package src.cube;

import src.cube.solver.Node;

public class Hashtest {
    public static void main(String[] args) {
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