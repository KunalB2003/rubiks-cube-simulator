package src.cube;

import static src.cube.Face.*;

public class Move {
    protected Face face;
    protected int num;

    public Move(Face face, int num) {
        this.face = face;
        this.num = num;
    }

    @Override
    public String toString() {
        return face.getStr() + ((num == 2) ? "2" : ((num == 3) ? "\'" : ""));
    }

    public static Move[] allMoves() {
        Move[] allMoves = new Move[18];

        for (int i = 0; i < 3; i++) {
            allMoves[(i * 3) + 0] = new Move(UP, (i + 1));
            allMoves[(i * 3) + 1] = new Move(DOWN, (i + 1));
            allMoves[(i * 3) + 2] = new Move(LEFT, (i + 1));
            allMoves[(i * 3) + 3] = new Move(RIGHT, (i + 1));
            allMoves[(i * 3) + 4] = new Move(FRONT, (i + 1));
            allMoves[(i * 3) + 5] = new Move(BACK, (i + 1));
        }

        return allMoves;
    }
}