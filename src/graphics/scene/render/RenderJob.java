package src.graphics.scene.render;

import src.graphics.Shader;
import src.graphics.shapes.Mesh;
import src.graphics.transform.controller.PositionController;
import src.graphics.transform.controller.RotationController;

public class RenderJob {

    private Mesh mesh;
    private Shader shader;
    private PositionController tPos;
    private RotationController tRot;

    public RenderJob(Mesh mesh, Shader shader, PositionController tPos, RotationController tRot) {
        this.mesh = mesh;
        this.shader = shader;
        this.tPos = tPos;
        this.tRot = tRot;
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
}
