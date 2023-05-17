package src.cube;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scramble {
    private Cube cube;
    private List<Move> moves;

    public Scramble(Random r, int num) {
        moves = IntStream
                .iterate(r.nextInt(6), t -> {
                    int j = r.nextInt(5);
                    return (j >= t) ? j + 1 : j;
                })
                .limit(num)
                .boxed()
                .map(t -> new Move(Face.intMap.get(t), r.nextInt(3) + 1))
                .toList();
        // Iterator<Move> movesIter = moves.iterator();
        // cube = Stream
        // .iterate(new Cube(), c -> movesIter.hasNext(), c -> c.move(movesIter.next()))
        // .reduce((a, b) -> b)
        // .orElse(null);
        cube = new Cube();
        moves.forEach(m -> cube = cube.move(m));
    }

    public Cube getCube() {
        return cube;
    }

    public String toString() {
        return moves.stream().map(Move::toString).collect(Collectors.joining(" "));
    }
}
