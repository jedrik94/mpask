package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class ValueExtractor {
    static public List<String> getValues(String line) {
        String values = Stream.of(line).map(s -> s.replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "")
                .replaceAll("^\\d+(\\.\\d+)*.?\\s",""))
                .findAny().orElse("");

        Matcher m = Pattern.compile("(\\d+(\\.\\d+)+)|(\\d+)|(\".+?\")|([0-9a-z]+)").matcher(values);

        List<String> valuesList = new ArrayList<>();

        while (m.find()) {
            valuesList.add(m.group());
        }
        return valuesList;
    }
}
