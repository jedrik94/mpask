package util.providers;

import mib.tree.DataType;
import util.NewDataTypeCreator;
import util.Parser;

import java.util.List;

public class NewDataTypeProvider {
    public static void defineNewDataTypesStrings(List<String> listNewObjectTypesText, List<String> filesText) {
        filesText.forEach(text -> Parser.collectMatches((Parser.objectTypeParser(text.replaceAll("((--)[\\s\\S]*?[^\\n]*)", " "), Parser.DATA_TYPES)))
                .forEach(s -> listNewObjectTypesText.add(s.replaceAll("::=", "")
                        .replaceAll("\\[|\\]|\\(|\\)|SIZE", "")
                        .replaceAll("\\.\\.", " ")
                        .replaceAll("OCTET STRING", "OCTET_STRING")
                        .replaceAll("CONTEXT-SPECIFIC", "CONTEXT_SPECIFIC")
                        .replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", ""))));
    }

    public static void createNewDataTypes(List<DataType> dataTypes, List<String> listNewObjectTypesText) {
        listNewObjectTypesText.forEach(ot -> {
            String[] temp = ot.split("\\s");
            dataTypes.add(NewDataTypeCreator.getNewDataType(temp));
        });
    }
}