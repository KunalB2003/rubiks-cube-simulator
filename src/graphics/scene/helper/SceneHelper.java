package src.graphics.scene.helper;

import java.util.ArrayList;

public class SceneHelper<T> {

    private ArrayList<Pair<String, T>> list;

    public SceneHelper() {
        this.list = new ArrayList<Pair<String, T>>();
    }

    public void add(String name, T entry) {
        list.add(new Pair<String, T>(name, entry));
    }

    public ArrayList<Pair<String, T>> getList() {
        return list;
    }

}
