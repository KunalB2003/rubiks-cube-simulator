package src.cube;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Face {
    DOWN(3, "D"), UP(2, "U"), FRONT(0, "F"), BACK(5, "B"), LEFT(1, "L"), RIGHT(4, "R");

    private final int val;
    private final String str;
    public static final Map<Integer, Face> intMap = Collections.unmodifiableMap(initializeIntMapping());
    public static final Map<String, Face> strMap = Collections.unmodifiableMap(initializeStringMapping());

    private Face(int val, String str) {
        this.val = val;
        this.str = str;
    }

    private static Map<Integer, Face> initializeIntMapping() {
        Map<Integer, Face> intMap = new HashMap<Integer, Face>();
        for (Face f : Face.values()) {
            intMap.put(f.val, f);
        }
        return intMap;
    }

    private static Map<String, Face> initializeStringMapping() {
        Map<String, Face> strMap = new HashMap<String, Face>();
        for (Face f : Face.values()) {
            strMap.put(f.str, f);
        }
        return strMap;
    }

    public int getVal() {
        return val;
    }

    public String getStr() {
        return str;
    }

}