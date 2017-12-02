package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static final String OBJECT_TYPE_REGEX = "\\n\\w+\\s(OBJECT-TYPE)[\\s\\S]*?(::=)\\s(.*\\s\\w+\\s\\d+\\s.*)";
    public static final String IMPORTS_REGEX = "(IMPORTS)[\\s\\S]*?[^;]*";
    public static final String OBJECT_IDENTIFIER_REGEX = ".+?(OBJECT IDENTIFIER)\\s(::=)\\s(.*\\s.+?\\s\\d+\\s.*)";
    public static final String ELEMENTS_IMPORT_FILE_REGEX = "[\\s\\S]*?(FROM)\\s([A-Za-z0-9-])+";
    public static final String DATA_TYPES = "(.+?)\\s::=\\s+?\\[([\\w]+)\\s(\\d)\\](?:\\s+?)(\\w+)\\s(\\w+\\s\\w+|\\w+)\\s((?:\\()([\\s\\S]+?)(?:\\))|)";

    public static Matcher objectTypeParser(String text, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(text);
    }

    public static List<String> collectMatches(Matcher patternMatcher) {
        List<String> matchesList = new ArrayList<String>();

        while (patternMatcher.find()) {
            matchesList.add(patternMatcher.group());
        }

        return matchesList;
    }

}
