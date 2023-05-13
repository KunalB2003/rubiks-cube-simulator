package src.graphics.scene.render;

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

    private float objectDistance() {
        System.out.println(mesh.getVertices());
        
        return 1.0f;
    }
}
