package src.graphics;

import java.util.HashMap;

import src.graphics.shapes.Mesh;

public class Scene {

    private HashMap<String, Shader> shaders;
    private HashMap<String, Mesh> meshes;
    private HashMap<String, Transform> transforms;

    private Camera sceneCamera;

    public Scene(Camera sceneCamera) {
        shaders = new HashMap<String, Shader>();
        meshes = new HashMap<String, Mesh>();
        transforms = new HashMap<String, Transform>();

        this.sceneCamera = sceneCamera;
    }

    public void renderMesh(String mesh, String shader, String transform) {
        shaders.get(shader).useShader();
        shaders.get(shader).setCamera(sceneCamera);
        shaders.get(shader).setTransform(transform == null ? new Transform() : transforms.get(transform));

        meshes.get(mesh).draw();
    }

    public HashMap<String, Shader> getShaders() {
        return shaders;
    }

    public void registerShader(String name, Shader shader) {
        shaders.put(name, shader);
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

    public HashMap<String, Transform> getTransforms() {
        return transforms;
    }

    public void registerTransform(String name, Transform transform) {
        transforms.put(name, transform);
    }

    public void removeTransform(String name) {
        transforms.remove(name);
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
