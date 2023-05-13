package src.graphics.shapes;

public class Quad extends Mesh {

    private Mesh mesh1, mesh2;

    public Quad() {
        super();
        mesh1 = new Mesh();
        mesh2 = new Mesh();
    }

    @Override
    public Quad create(float v[]) {
        if (v.length != 12) {
            throw new IllegalArgumentException("Requires 12 coordinates. If 9 coordinates, make a [Mesh] Object instead");
        }

        mesh1.create(new float[] {
                v[0], v[1], v[2],
                v[3], v[4], v[5],
                v[6], v[7], v[8]
        });
        mesh2.create(new float[] {
                v[6], v[7], v[8],
                v[9], v[10], v[11],
                v[0], v[1], v[2]
        });

        return this;
    }

    @Override
    public void destroy() {
        mesh1.destroy();
        mesh2.destroy();
    }

    @Override
    public void draw() {
        mesh1.draw();
        mesh2.draw();
    }
}
