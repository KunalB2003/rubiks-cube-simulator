package src.graphics;

import java.util.HashMap;

import src.graphics.shapes.Mesh;
import src.graphics.transform.PositionController;
import src.graphics.transform.RotationController;
import src.graphics.transform.Transform;

public class Scene {

    private HashMap<String, Shader> shaders;
    private HashMap<String, Mesh> meshes;

    private Camera sceneCamera;

    public Scene(Camera sceneCamera) {
        shaders = new HashMap<String, Shader>();
        meshes = new HashMap<String, Mesh>();

        this.sceneCamera = sceneCamera;
    }

    public void renderMesh(String mesh, String shader, PositionController tPos, RotationController tRot) {
        renderMesh(meshes.get(mesh), shaders.get(shader), tPos, tRot);
    }

    public void renderMesh(Mesh mesh, Shader shader, PositionController tPos, RotationController tRot) {
        Transform transform = mesh.getTransform();
        if (tPos != null) {
            tPos.changePosition(transform);
        }
        if (tRot != null) {
            tRot.changeRotation(transform);
        }
        shader.useShader();
        shader.setCamera(sceneCamera);
        shader.setTransform(transform);

        mesh.draw();
    }

    public HashMap<String, Shader> getShaders() {
        return shaders;
    }

    public void registerShader(String name) {
        shaders.put(name, new Shader().create(name));
    }

    public void removeShader(String name) {
        shaders.get(name).destroy();
        shaders.remove(name);
    }

    public HashMap<String, Mesh> getMeshes() {
        return meshes;
    }

    public void registerMesh(String name, Mesh mesh) {
        meshes.put(name, mesh);
    }

    public void removeMesh(String name) {
        meshes.get(name).destroy();
        meshes.remove(name);
    }

    public Camera getCamera() {
        return sceneCamera;
    }

    public void destroy() {
        shaders.forEach((name, shader) -> {
            shader.destroy();
        });

        meshes.forEach((name, mesh) -> {
            mesh.destroy();
        });
    }
}
