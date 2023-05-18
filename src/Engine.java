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

public class Engine {

        public static int[] frame = { 0 };
        public static boolean paused = false;

        public static void main(String[] args) {
                int width = 1600;
                int height = 900;

                Window window = new Window(width, height);
                window.createWindow("Rubik's Cube Simulator");

                Camera c = new Camera();
                c.setPosition(new Vector3f(0, 1, 3));
                c.setRotation(new Quaternionf(new AxisAngle4f((float) Math.toRadians(-10), new Vector3f(1, 0, 0))));
                c.setPerspective((float) Math.toRadians(50), (float) ((width * 1.0) / (height * 1.0)), 0.001f, 1000.0f);

                Scene scene = new Scene(c);
                SceneHelper<Mesh> meshes = new SceneHelper<Mesh>();
                SceneHelper<Shader> shaders = new SceneHelper<Shader>();

                meshes.add("floorPlane", new Quad(new float[] {
                                3, 0, 0,
                                0, 0, 3,
                                -3, 0, 0,
                                0, 0, -3,
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

                shaders.add("floorWhite", new Shader().create(new float[] { 0.7f, 0.7f, 0.7f }));
                shaders.add("cyan", new Shader().create(new float[] { 0, 1, 0.67f }));
                shaders.add("cyan", new Shader().create(new float[] { 0, 1, 0.67f }));

                shaders.add("cubeGreen", new Shader().create(new float[] { 0, 0.61f, 0.28f }));
                shaders.add("cubeWhite", new Shader().create(new float[] { 1, 1, 1 }));
                shaders.add("cubeRed", new Shader().create(new float[] { 0.72f, 0.07f, 0.20f }));
                shaders.add("cubeYellow", new Shader().create(new float[] { 1, 0.84f, 0 }));
                shaders.add("cubeBlue", new Shader().create(new float[] { 0, 0.27f, 0.68f }));
                shaders.add("cubeOrange", new Shader().create(new float[] { 1, 0.35f, 0 }));

                scene.registerMesh(meshes.getList());
                scene.registerShader(shaders.getList());

                while (!window.update()) {
                        if (!paused) {
                                glClear(GL_COLOR_BUFFER_BIT);

                                scene.queue("floorPlane", "floorWhite",
                                                null, null);

                                scene.queue("square", "cubeYellow",
                                                null, null);

                                scene.queue("tri2", "cubeWhite",
                                                null, null);

                                scene.renderCamOrdered();
                                window.swapBuffers();
                                frame[0]++;
                        }
                }
                scene.destroy();
                window.free();
        }

}