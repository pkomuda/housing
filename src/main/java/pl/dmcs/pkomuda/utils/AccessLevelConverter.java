package pl.dmcs.pkomuda.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import pl.dmcs.pkomuda.model.AccessLevel;
import pl.dmcs.pkomuda.model.AccessLevelType;

public class AccessLevelConverter implements Converter<String, AccessLevel> {

    @Override
    public AccessLevel convert(@NonNull String label) {
        return new AccessLevel(AccessLevelType.valueOfLabel(label));
    }
}
