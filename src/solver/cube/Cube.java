package src.solver.cube;

public class Cube {

    private int[][] states;

    public Cube() {
        instantiateCube();
    }

    private void instantiateCube() {
        states = new int[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                states[i][j] = i;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("   ");
        sb.append(states[2][0]);
        sb.append(states[2][1]);
        sb.append(states[2][2]);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0 || j == 2) {
                    if (i == 0 || i == 2 || i == 3) {

                    }
                }
            }
            sb.append("\n");
        }




        return sb.toString();
    }
}
