package src;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private int vertexArrayObject;
    private int vertexBufferObject;

    private int vertexCount;

    public Mesh() {
    }

    public boolean create(float vertices[]) {
        vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        glBindVertexArray(0);
        return true;
    }

    public void destroy() {
        glDeleteBuffers(vertexBufferObject);
        glDeleteVertexArrays(vertexArrayObject);
    }
}
