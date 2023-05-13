package src;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Main {

    public static void main(String[] args) {
        Window window = new Window();

        window.createWindow(640, 480);

        Mesh testMesh = new Mesh();
        testMesh.create(new float[] {
                -1, -1, 0,
                0, 1, 0,
                1, -1, 0
        });

        boolean isRunning = true;

        while (!isRunning) {
            isRunning = !window.update();

            glClear(GL_COLOR_BUFFER_BIT);

            testMesh.draw();

            window.swapBuffers();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        testMesh.destroy();

        window.free();

    }

}