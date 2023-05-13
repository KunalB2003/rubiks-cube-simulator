package src;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import src.display.Window;
import src.graphics.Camera;
import src.graphics.scene.Scene;
import src.graphics.scene.helper.SceneHelper;
import src.graphics.shapes.Mesh;
import src.graphics.shapes.Quad;

public class Main {

        public static void main(String[] args) {
                int width = 960;
                int height = 720;

                Window window = new Window(width, height);
                window.createWindow("Rubik's Cube Simulator");

                Camera c = new Camera();
                c.setPosition(new Vector3f(0, 1, 3));
                c.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-30), new Vector3f(1, 0, 0))));
                c.setPerspective((float) Math.toRadians(70), (float) ((width * 1.0) / (height * 1.0)), 0.001f, 1000.0f);

                Scene scene = new Scene(c);
                SceneHelper<Mesh> meshes = new SceneHelper<Mesh>();
                SceneHelper<float[]> shaders = new SceneHelper<float[]>();

                meshes.add("tri1", new Mesh().create(new float[] {
                                -1, -1, -1,
                                -1, 0, -1,
                                0, 0, -1,
                }));
                meshes.add("tri2", new Mesh().create(new float[] {
                                0, 0, 0,
                                -1, -1, 0,
                                0, -1, 0
                }));
                meshes.add("square", new Quad().create(new float[] {
                                -1, -1, 0,
                                -1, 1, 0,
                                1, 1, 0,
                                1, -1, 0
                }));

                shaders.add("cyan", new float[] { 0, 1, 0.67f });
                shaders.add("red", new float[] { 1, 0, 0 });
                shaders.add("purple", new float[] { 0.82f, 0.0f, 1.0f });

                scene.registerMesh(meshes.getList());
                scene.registerShader(shaders.getList());

                System.out.println(scene.getMeshes());
                System.out.println(scene.getShaders());

                float[] frame = { 0 };
                while (!window.update()) {
                        glClear(GL_COLOR_BUFFER_BIT);

                        scene.renderMesh("square", "purple",
                                        (t) -> {
                                                t.setPosition(new Vector3f(
                                                                (float) Math.sin(Math.toRadians(frame[0])), 0, 0));
                                        },
                                        (t) -> {
                                                t.getRotation().rotateAxis((float) Math.toRadians(1), 0, 1, 0);
                                        });

                        scene.renderMesh("tri1", "cyan",
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