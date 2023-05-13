package src.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import src.graphics.transform.Transform;

public class Shader {
    private int vertexShader, fragmentShader, program;
    private int uniMatProjection, uniMatTransformWorld, uniMatTransformObject, uniColor;

    private float[] color;

    public Shader() {
    }

    public Shader create(float[] color) {
        if (color.length != 3) {
            throw new IllegalArgumentException("color parameter requires exactly 3 values for r, g, and b for shader.");
        }
        this.color = color;

        int success;

        vertexShader = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexShader, readSource("basic.vs"));

        glCompileShader(vertexShader);

        success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            System.err.println("Vertex: \n" + glGetShaderInfoLog(vertexShader));
            return null;
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, readSource("basic.fs"));

        glCompileShader(fragmentShader);

        success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            System.err.println("Fragment: \n" + glGetShaderInfoLog(fragmentShader));
            return null;
        }

        program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);
        success = glGetProgrami(program, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            System.err.println("Program Link: \n" + glGetProgramInfoLog(program));
            return null;
        }
        glValidateProgram(program);
        success = glGetProgrami(program, GL_VALIDATE_STATUS);
        if (success == GL_FALSE) {
            System.err.println("Program Validate: \n" + glGetProgramInfoLog(program));
            return null;
        }

        uniMatProjection = glGetUniformLocation(program, "cameraProjection");
        uniMatTransformWorld = glGetUniformLocation(program, "transformWorld");
        uniMatTransformObject = glGetUniformLocation(program, "transformObject");
        uniColor = glGetUniformLocation(program, "color");

        return this;
    }

    public void destroy() {
        glDetachShader(program, vertexShader);
        glDetachShader(program, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteShader(program);
    }

    public void useShader() {
        glUseProgram(program);
    }

    public void setCamera(Camera camera) {
        if (uniMatProjection != -1) {
            float matrix[] = new float[16];
            camera.getProjection().get(matrix);
            glUniformMatrix4fv(uniMatProjection, false, matrix);
        }
        if (uniMatTransformWorld != -1) {
            float matrix[] = new float[16];
            camera.getTransformation().get(matrix);
            glUniformMatrix4fv(uniMatTransformWorld, false, matrix);
        }
    }

    public void setColor() {
        if (uniColor != -1) {
            glUniform4f(uniColor, color[0], color[1], color[2], 1);
        }
    }

    public void setTransform(Transform transform) {
        if (uniMatTransformObject != -1) {
            float matrix[] = new float[16];
            transform.getTransformation().get(matrix);
            glUniformMatrix4fv(uniMatTransformObject, false, matrix);
        }
    }

    private String readSource(String file) {
        BufferedReader reader = null;
        StringBuilder sourceBuilder = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shaders/basic/" + file)));

            String line;

            while ((line = reader.readLine()) != null) {
                sourceBuilder.append(line + "\n");
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

    @Override
    public String toString() {
        return "Shader [color=" + Arrays.toString(color) + "]";
    }   
}
