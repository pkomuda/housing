package pl.dmcs.pkomuda.model;

import java.util.HashMap;
import java.util.Map;

public enum AccessLevelType {

    ADMIN("access.level.admin"),
    MANAGER("access.level.manager"),
    RESIDENT("access.level.resident");

    private static final Map<String, AccessLevelType> BY_LABEL = new HashMap<>();

    static {
        for (AccessLevelType accessLevelType : values()) {
            BY_LABEL.put(accessLevelType.label, accessLevelType);
        }
    }

    public final String label;

    AccessLevelType(String label) {
        this.label = label;
    }

    public static AccessLevelType valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
