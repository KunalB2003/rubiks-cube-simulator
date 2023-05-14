package src.solver.cube;

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
}