package src.cube;

import static src.cube.Face.BACK;
import static src.cube.Face.DOWN;
import static src.cube.Face.FRONT;
import static src.cube.Face.LEFT;
import static src.cube.Face.RIGHT;
import static src.cube.Face.UP;

public class Move {
    protected Face face;
    protected int numMoves;

    public Move(Face face, int numMoves) {
        this.face = face;
        this.numMoves = numMoves;
    }

    @Override
    public String toString() {
        return face.getStr() + ((numMoves == 2) ? "2" : ((numMoves == 3) ? "\'" : ""));
    }

    public static Move[] allMoves() {
        Move[] allMoves = new Move[18];

        for (int i = 0; i < 3; i++) {
            allMoves[(i * 6) + 0] = new Move(UP, (i + 1));
            allMoves[(i * 6) + 1] = new Move(DOWN, (i + 1));
            allMoves[(i * 6) + 2] = new Move(LEFT, (i + 1));
            allMoves[(i * 6) + 3] = new Move(RIGHT, (i + 1));
            allMoves[(i * 6) + 4] = new Move(FRONT, (i + 1));
            allMoves[(i * 6) + 5] = new Move(BACK, (i + 1));
        }

        return allMoves;
    }

    public Move getReverse() {
        return new Move(this.face, 4-this.numMoves);
    }
}