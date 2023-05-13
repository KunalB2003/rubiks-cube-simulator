package src.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shader {
    private int vertexShader, fragmentShader, program;

    public Shader() {
    }

    public boolean create(String shader) {
        int success;

        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, readSource(shader + ".vs"));
        glCompileShader(vertexShader);

        success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
    }

    public void destroy() {

    }

    public void useShader() {

    }

    private String readSource(String file) {
        BufferedReader reader = null;
        StringBuilder sourceBuilder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shaders/" + file)));

            String line;

            while ((line = reader.readLine()) != null) {
                sourceBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sourceBuilder.toString();
    }

}
