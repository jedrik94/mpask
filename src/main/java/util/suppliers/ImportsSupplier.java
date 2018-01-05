package util.suppliers;

import common.Constants;
import util.FileToStringReader;
import util.Parser;
import util.StringValueExtractor;

import java.util.List;

public class ImportsSupplier {
    public static boolean recursionImports(List<String> list, List<String> filesText) {
        filesText.forEach(s -> collectImportsFromText(s, list));

        return importsFileTextToList(list, filesText);
    }

    private static boolean importsFileTextToList(List<String> list, List<String> importsText) {
        int staringListSize = importsText.size();
        list.forEach(s -> {
            String tempString = Constants.FILE_FOLDER_PATH + StringValueExtractor.extract(s, StringValueExtractor.EnabledTokens.IMPORT_FILE_NAME);
            if (!FileToStringReader.convertTextFileToSting(tempString).isEmpty()) {
                importsText.add(0, FileToStringReader.convertTextFileToSting(tempString));
            } else if (!FileToStringReader.convertTextFileToSting(tempString + ".txt").isEmpty()) {
                importsText.add(0, FileToStringReader.convertTextFileToSting(tempString + ".txt"));
            }
        });
        return importsText.size() == staringListSize;
    }

    private static void collectImportsFromText(String mainText, List<String> list) {
        Parser.collectMatches(Parser.objectTypeParser(mainText, Parser.IMPORTS_REGEX))
                .forEach(s -> Parser.collectMatches(Parser.objectTypeParser(s, Parser.ELEMENTS_IMPORT_FILE_REGEX))
                        .forEach(so -> list.add(so.replaceAll("(IMPORTS)", "")
                                .replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", ""))));

    }
}