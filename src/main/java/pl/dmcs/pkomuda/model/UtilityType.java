package pl.dmcs.pkomuda.model;

public enum UtilityType {

    ELECTRICITY("utility.electricity"),
    GAS("utility.gas"),
    WATER("utility.water");

    public final String label;

    UtilityType(String label) {
        this.label = label;
    }
}
