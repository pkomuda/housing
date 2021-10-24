package pl.dmcs.pkomuda.housing.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import pl.dmcs.pkomuda.housing.model.AccessLevel;
import pl.dmcs.pkomuda.housing.model.AccessLevelType;

public class AccessLevelConverter implements Converter<String, AccessLevel> {

    @Override
    public AccessLevel convert(@NonNull String label) {
        return new AccessLevel(AccessLevelType.valueOfLabel(label));
    }
}
