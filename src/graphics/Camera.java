package src.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
    private Vector3f position;
    private Quaternionf rotation;
    private Matrix4f projection;
    
    
    public Camera() {
        position = new Vector3f();
        rotation = new Quaternionf();
        projection = new Matrix4f();
    }
    
    public Matrix4f getTransformation() {
        Matrix4f returnValue = new Matrix4f();

        returnValue.rotate(rotation.conjugate(new Quaternionf()));
        returnValue.translate(position.mul(-1, new Vector3f()));

        return returnValue;
    }

    public void setOrthographic(float left, float right, float top, float bottom) {
        projection.setOrtho2D(left, right, bottom, top);
    }

    public void setPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        projection.setPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    @Override
    public String toString() {
        return "Camera [position=" + position + ", rotation=" + rotation + ", projection=" + projection + "]";
    }
}
