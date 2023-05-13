package src.graphics;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f scale;

    public Transform() {
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f(1);
    }
}
