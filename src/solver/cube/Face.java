package src.solver.cube;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Face {
    DOWN(3, "D"), UP(2, "U"), FRONT(0, "F"), BACK(5, "B"), LEFT(1, "L"), RIGHT(4, "R");

    private final int val;
    private final String str;
    private static final Map<String, Face> mMap = Collections.unmodifiableMap(initializeMapping());

    private Face(int val, String str) {
        this.val = val;
        this.str = str;
    }

    private static Map<String, Face> initializeMapping() {
        Map<String, Face> faceMap = new HashMap<String, Face>();
        for (Face f : Face.values()) {
            faceMap.put(f.str, f);
        }
        return faceMap;
    }

    public int getVal() {
        return val;
    }

    public String getStr() {
        return str;
    }

}