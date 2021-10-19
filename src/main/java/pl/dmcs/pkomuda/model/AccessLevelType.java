package pl.dmcs.pkomuda.model;

public enum AccessLevelType {

    ADMIN("access.level.admin"),
    MANAGER("access.level.manager"),
    RESIDENT("access.level.resident");

    public final String label;

    AccessLevelType(String label) {
        this.label = label;
    }
}
