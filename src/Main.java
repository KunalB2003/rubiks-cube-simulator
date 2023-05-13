package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import src.graphics.Camera;
import src.graphics.Mesh;
import src.graphics.Shader;
import src.graphics.Transform;
import src.io.Window;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int width = 640;
        int height = 480;

        Window window = new Window(width, height);
        window.createWindow("Window");

        Mesh mesh = new Mesh();
        mesh.create(new float[] {
                -1, -1, 0,
                0, 1, 0,
                1, -1, 0
        });

        Shader shader = new Shader();
        shader.create("basic");

        Camera camera = new Camera();
        Transform transform = new Transform();

        camera.setPerspective((float) Math.toRadians(70), (float) ((width * 1.0) / (height * 1.0)), 0.01f, 1000.0f);
        camera.setPosition(new Vector3f(0, 1, 3));
        camera.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));

        boolean isRunning = true;
        float x = 0;

        while (isRunning) {
            isRunning = !window.update();

            x++;
            transform.setPosition(new Vector3f((float) Math.sin(Math.toRadians(x)), 0, 0));
            transform.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);

            glClear(GL_COLOR_BUFFER_BIT);

            shader.useShader();
            shader.setCamera(camera);
            shader.setTransform(transform);
            mesh.draw();

            window.swapBuffers();

            Thread.sleep(1);
        }

        mesh.destroy();
        shader.destroy();

        window.free();

    }

}