package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringValueExtractor {

    public enum EnabledTokens {
        UID, PARENT, NODE_NAME, IMPORT_FILE_NAME
    }

    public static String extractValue(String stringToExtractFrom, String variableName, String[] variablesNamesMIB) {
        return stringToExtractFrom.contains(variableName)
                ? collectMatches(objectTypeParser(stringToExtractFrom, "(" + variableName + ")\\s+[\\s\\S]*?[^\\n]*(::=|" + createRegexORForStrings(variablesNamesMIB) + ")"))
                .replaceAll("::=|" + createRegexORForStrings(variablesNamesMIB), "")
                .replaceAll("((--)[\\s\\S]*?[^\\n]*)", "")
                .replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "")
                : "_empty";
    }

    public static String extract(String stringToExtractFrom, EnabledTokens token) {
        String stringAnalyzedPart = "";
        String tempRegexPattern = "";

        switch (token) {
            case UID:
                tempRegexPattern = "\\s\\d+\\s";
                stringAnalyzedPart = collectMatches(objectTypeParser(stringToExtractFrom, "(::=)\\s.[\\s\\S]*?}"));
                break;
            case PARENT:
                tempRegexPattern = "([A-Za-z0-9-])+";
                stringAnalyzedPart = collectMatches(objectTypeParser(stringToExtractFrom, "(::=)\\s.[\\s\\S]*?}"));
                break;
            case NODE_NAME:
                tempRegexPattern = "\\w+";
                stringAnalyzedPart = stringToExtractFrom;
                break;
            case IMPORT_FILE_NAME:
                tempRegexPattern = "\\s[\\s\\S]+";
                stringAnalyzedPart = collectMatches(objectTypeParser(stringToExtractFrom, "(FROM)\\s[\\s\\S]+"));
        }

        return collectMatches(objectTypeParser(stringAnalyzedPart, tempRegexPattern)).replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "");
    }

    private static Matcher objectTypeParser(String text, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(text);
    }

    private static String collectMatches(Matcher patternMatcher) {
        return patternMatcher.find() ? patternMatcher.group() : "_empty";
    }

    private static String createRegexORForStrings(String[] array) {
        StringBuilder resultString = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            resultString.append(array[i]);
            if (i != array.length - 1) {
                resultString.append("|");
            }
        }
        return resultString.toString();
    }
}
