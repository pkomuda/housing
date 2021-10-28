package pl.dmcs.pkomuda.housing.exceptions;

public class BuildingNotFoundException extends ApplicationBaseException {

    public static final String KEY_BUILDING_NOT_FOUND = "error.building.not.found";

    public BuildingNotFoundException() {
        super(KEY_BUILDING_NOT_FOUND);
    }

    public BuildingNotFoundException(Throwable cause) {
        super(KEY_BUILDING_NOT_FOUND, cause);
    }
}
