package util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OIDSplitter {
    static public List<Integer> getOIDPath(String line) {
        return Arrays.stream(line.replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "").replaceAll("\\s[\\s\\S]*.?", "").split("\\."))
               .map(Integer::parseInt)
               .collect(Collectors.toList());
    }
}
