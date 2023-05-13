package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import src.display.Window;
import src.graphics.Camera;
import src.graphics.Scene;
import src.graphics.shapes.Mesh;
import src.graphics.shapes.Quad;

public class Main {

        public static void main(String[] args) {
                int width = 640;
                int height = 480;

                Window window = new Window(width, height);
                window.createWindow("Window");

                Camera sC = new Camera();
                sC.setPerspective((float) Math.toRadians(70), (float) ((width * 1.0) / (height * 1.0)), 0.001f,
                                1000.0f);
                sC.setPosition(new Vector3f(0, 1, 3));
                sC.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));

                Scene scene = new Scene(sC);

                scene.registerMesh("triangle1", new Mesh().create(new float[] {
                                -1, -1, 1,
                                -1, 0, 2,
                                0, 0, 3,
                }));
                scene.registerMesh("triangle2", new Mesh().create(new float[] {
                                0, 0, 0,
                                -1, -1, 0,
                                0, -1, 0
                }));
                scene.registerMesh("square", new Quad().create(new float[] {
                                -1, -1, 0,
                                -1, 1, 0,
                                1, 1, 0,
                                1, -1, 0
                }));

                scene.registerShader("cyan", new float[] { 0, 1, 0.67f });
                scene.registerShader("red", new float[] { 1, 0, 0 });
                scene.registerShader("purple", new float[] { 0.82f, 0.0f, 1.0f });

                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

                float[] frame = { 0 };
                while (!window.update()) {
                        // glDisable(GL_BLEND);
                        glClear(GL_COLOR_BUFFER_BIT);

                        scene.renderMesh("square", "purple",
                                        (t) -> {
                                                t.setPosition(new Vector3f(
                                                                (float) Math.sin(Math.toRadians(frame[0])), 0, 0));
                                        },
                                        (t) -> {
                                                t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                        });
                        scene.renderMesh("triangle1", "cyan",
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