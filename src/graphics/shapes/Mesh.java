package src.graphics.shapes;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import src.graphics.transform.Transform;

public class Mesh {
    private int vertexArrayObject;
    private int vertexBufferObject;
    private int vertexCount;
    private Transform transform;
    private float[] vertices;

    public Mesh(float[] vertices) {
        this.vertices = vertices;
        transform = new Transform();
    }

    public Mesh create() {
        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindVertexArray(0);

        vertexCount = vertices.length / 3;

        return this;
    }

    public void destroy() {
        glDeleteBuffers(vertexBufferObject);
        glDeleteVertexArrays(vertexArrayObject);
    }

    public void draw() {
        glBindVertexArray(vertexArrayObject);

        glEnableVertexAttribArray(0);

        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    public Transform getTransform() {
        return transform;
    }

    public float[] getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Mesh [vertexArrayObject=" + vertexArrayObject + ", vertexBufferObject=" + vertexBufferObject
                + ", vertexCount=" + vertexCount + ", transform=" + transform + "]";
    }
}
