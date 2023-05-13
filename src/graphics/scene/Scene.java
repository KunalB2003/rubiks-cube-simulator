package src.graphics.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import src.graphics.Camera;
import src.graphics.Shader;
import src.graphics.scene.helper.Pair;
import src.graphics.scene.render.RenderJob;
import src.graphics.shapes.Mesh;
import src.graphics.transform.Transform;
import src.graphics.transform.controller.PositionController;
import src.graphics.transform.controller.RotationController;

public class Scene {

    private HashMap<String, Shader> shaders;
    private HashMap<String, Mesh> meshes;
    private Camera sceneCamera;
    private ArrayList<RenderJob> renderJobs;
    private RenderJob[] orderedJobs;

    public Scene(Camera sceneCamera) {
        shaders = new HashMap<String, Shader>();
        meshes = new HashMap<String, Mesh>();
        this.renderJobs = new ArrayList<RenderJob>();
        this.sceneCamera = sceneCamera;
    }

    public void queue(String mesh, String shader, PositionController tPos, RotationController tRot) {
        queue(new RenderJob(meshes.get(mesh), shaders.get(shader), tPos, tRot, sceneCamera));
    }

    public void queue(RenderJob job) {
        renderJobs.add(job);
    }

    public void renderCamOrdered() {
        orderedJobs = new RenderJob[renderJobs.size()];
        renderJobs.toArray(orderedJobs);
        Arrays.sort(orderedJobs, 0, orderedJobs.length, (j1, j2) -> {
            return (int) ((j1.objectDistance() - j2.objectDistance()) * 1000);
        });
        for (int i = 0; i < orderedJobs.length; i++) {
            RenderJob job = orderedJobs[i];
            renderMesh(job.getMesh(), job.getShader(), job.gettPos(), job.gettRot());
        }
        clearRenderQueue();
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
        shader.setColor();

        mesh.draw();
    }

    public HashMap<String, Shader> getShaders() {
        return shaders;
    }

    public void registerShader(ArrayList<Pair<String, Shader>> shaderList) {
        shaderList.forEach((p) -> {
            shaders.put(p.first, p.second);
        });
    }

    public void removeShader(String name) {
        shaders.get(name).destroy();
        shaders.remove(name);
    }

    public HashMap<String, Mesh> getMeshes() {
        return meshes;
    }

    public void registerMesh(ArrayList<Pair<String, Mesh>> meshList) {
        meshList.forEach((p) -> {
            meshes.put(p.first, p.second);
        });
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

    public void clearRenderQueue() {
        renderJobs.clear();
    }
}
