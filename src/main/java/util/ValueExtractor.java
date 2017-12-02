package util;

import java.util.stream.Stream;

public final class ValueExtractor {
    static public String getValue(String line) {
        return Stream.of(line).map(s -> s.replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "").replaceAll("\\d+(\\.\\d+)*.?\\s","")).findAny().orElse("");
    }
}
