package src.graphics.scene.render;

import org.lwjgl.system.linux.XNoExposeEvent;

import src.graphics.Camera;
import src.graphics.Shader;
import src.graphics.shapes.Mesh;
import src.graphics.transform.controller.PositionController;
import src.graphics.transform.controller.RotationController;

public class RenderJob {

    private Mesh mesh;
    private Shader shader;
    private PositionController tPos;
    private RotationController tRot;
    private Camera camera;
    private float objDist;

    public RenderJob(Mesh mesh, Shader shader, PositionController tPos, RotationController tRot, Camera camera) {
        this.mesh = mesh;
        this.shader = shader;
        this.tPos = tPos;
        this.tRot = tRot;
        this.camera = camera;
        this.objDist = objectDistance();
    }

    public float objectDistance() {
        float[] vertices = mesh.getVertices();
        
        float xN = 0;
        float yN = 0;
        float zN = 0;

        for (int i = 0; i < vertices.length / 3; i++) {
             xN += vertices[i];
             yN += vertices[i * 3 + 1];
             zN += vertices[i * 3 + 2];
        }

        xN /= vertices.length;
        yN /= vertices.length;
        zN /= vertices.length;

        tPos.changePosition(mesh.getTransform());

        xN += mesh.getTransform().getPosition().x;
        yN += mesh.getTransform().getPosition().y;
        zN += mesh.getTransform().getPosition().z;
        
        double dist = Math.sqrt(Math.pow(xN - camera.getPosition().x, 2) + Math.pow(yN - camera.getPosition().y, 2) + Math.pow(zN - camera.getPosition().z, 2));

        return (float)dist;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Shader getShader() {
        return shader;
    }

    public PositionController gettPos() {
        return tPos;
    }

    public RotationController gettRot() {
        return tRot;
    }

    public Camera getCamera() {
        return camera;
    }

    public float getObjDist() {
        return objDist;
    }


}
