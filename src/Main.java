package src;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import src.display.Window;
import src.graphics.Camera;
import src.graphics.Shader;
import src.graphics.scene.Scene;
import src.graphics.scene.helper.SceneHelper;
import src.graphics.shapes.Mesh;
import src.graphics.shapes.Quad;

public class Main {

        public static int[] frame = { 0 };
        public static boolean paused = false;

        public static void main(String[] args) {
                int width = 1600;
                int height = 900;

                Window window = new Window(width, height);
                window.createWindow("Rubik's Cube Simulator");

                Camera c = new Camera();
                c.setPosition(new Vector3f(0, 1, 3));
                c.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));
                c.setPerspective((float) Math.toRadians(70), (float) ((width * 1.0) / (height * 1.0)), 0.001f, 1000.0f);

                Scene scene = new Scene(c);
                SceneHelper<Mesh> meshes = new SceneHelper<Mesh>();
                SceneHelper<Shader> shaders = new SceneHelper<Shader>();

                meshes.add("tri1", new Mesh(new float[] {
                                1, 0, 0,
                                0, 0, 1,
                                -1, 0, 0,
                }).create());
                meshes.add("tri2", new Mesh(new float[] {
                                0, 0, -1.5f,
                                -1, -1, -1.5f,
                                0, -1, -1.5f
                }).create());
                meshes.add("square", new Quad(new float[] {
                                -1, -1, 0,
                                -1, 1, 0,
                                1, 1, 0,
                                1, -1, 0
                }).create());

                shaders.add("cyan", new Shader().create(new float[] { 0, 1, 0.67f }));
                shaders.add("red", new Shader().create(new float[] { 1, 0, 0 }));
                shaders.add("purple", new Shader().create(new float[] { 0.82f, 0.0f, 1.0f }));

                scene.registerMesh(meshes.getList());
                scene.registerShader(shaders.getList());

                while (!window.update()) {
                        if (!paused) {
                                glClear(GL_COLOR_BUFFER_BIT);

                                scene.queue("square", "purple",
                                                (t) -> {
                                                        t.setPosition(new Vector3f(
                                                                        (float) Math.sin(Math.toRadians(frame[0])), 0,
                                                                        0));
                                                },
                                                (t) -> {
                                                        t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                                });

                                scene.queue("tri1", "cyan",
                                                (t) -> {
                                                        t.setPosition(new Vector3f(
                                                                        (float) Math.sin(Math.toRadians(frame[0])), 0,
                                                                        0));
                                                },
                                                (t) -> {
                                                        t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                                });

                                scene.queue("tri2", "red",
                                                (t) -> {
                                                        t.setPosition(new Vector3f(
                                                                        (float) Math.sin(Math.toRadians(frame[0])), 0,
                                                                        0));
                                                },
                                                (t) -> {
                                                        t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                                });

                                scene.renderCamOrdered();
                                window.swapBuffers();
                                frame[0]++;
                        }
                }
                scene.destroy();
                window.free();
        }

}