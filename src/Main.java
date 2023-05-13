package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import src.display.Window;
import src.graphics.Camera;
import src.graphics.Scene;
import src.graphics.Shader;
import src.graphics.shapes.Mesh;
import src.graphics.shapes.Quad;
import src.graphics.transform.Transform;

public class Main {

        public static void main(String[] args) {
                int width = 640;
                int height = 480;

                Window window = new Window(width, height);

                window.createWindow("Window");
                Camera sceneCamera = new Camera();
                sceneCamera.setPerspective((float) Math.toRadians(70), (float) ((width * 1.0) / (height * 1.0)), 0.01f,
                                1000.0f);
                sceneCamera.setPosition(new Vector3f(0, 1, 3));
                sceneCamera
                                .setRotation(new Quaternionf(
                                                new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));

                Mesh mesh = new Mesh();
                mesh.create(new float[] {
                                -1, -1, 0,
                                -1, 0, 0,
                                0, 0, 0,
                });
                Mesh mesh1 = new Mesh();
                mesh1.create(new float[] {
                                0, 0, 0,
                                -1, -1, 0,
                                0, -1, 0
                });
                Quad quad = new Quad();
                quad.create(new float[] {
                                -1, -1, 0,
                                -1, 1, 0,
                                1, 1, 0,
                                1, -1, 0
                });

                Shader cyan = new Shader();
                cyan.create("cyan");
                Shader red = new Shader();
                red.create("red");

                Scene scene = new Scene(sceneCamera);

                scene.registerMesh("triangle1", mesh);
                scene.registerMesh("triangle2", mesh1);
                scene.registerMesh("square", quad);

                scene.registerShader("cyan", cyan);
                scene.registerShader("red", red);

                float[] frame = { 0 };
                boolean isRunning = true;
                while (isRunning) {
                        isRunning = !window.update();
                        glClear(GL_COLOR_BUFFER_BIT);

                        scene.renderMesh("square", "cyan",
                                        (t) -> {
                                                t.setPosition(new Vector3f(
                                                                (float) Math.sin(Math.toRadians(frame[0])), 0, 0));
                                        },
                                        (t) -> {
                                                t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                        });

                        window.swapBuffers();
                        frame[0]++;
                }

                scene.destroy();
                window.free();

        }

}