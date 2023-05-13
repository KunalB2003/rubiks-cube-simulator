package src.graphics.scene.helper;

import java.util.ArrayList;

import src.graphics.scene.Scene;
import src.graphics.shapes.Mesh;

public class SceneHelper<T> {

    private ArrayList<Pair<String, T>> list;
    private Scene scene;

    public SceneHelper(Scene scene) {
        this.list = new ArrayList<Pair<String, T>>();
        this.scene = scene;
    }

    public void add(Pair<String, T> entry) {
        list.add(entry);
    }

    public Scene registerAll() {
        if (list.get(0).second instanceof Mesh) {
            list.forEach((p) -> {
                scene.registerMesh(p.first, (Mesh)p.second);
            });
        } else { // Assuming shader
            list.forEach((p) -> {
                scene.registerShader(p.first, (float[])p.second);
            });
        }
        return scene;
    }

    public ArrayList<Pair<String, T>> getList() {
        return list;
    }

    public Scene getScene() {
        return scene;
    }

}
