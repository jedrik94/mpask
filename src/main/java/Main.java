import common.BaseType;
import common.CodingMethod;
import common.Constants;
import common.Visibility;
import mib.tree.BaseNode;
import mib.tree.DataType;
import mib.tree.MyNode;
import mib.tree.Root;
import util.*;
import util.coders.*;
import util.providers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String mainText = FileToStringReader.convertTextFileToSting(Constants.FILE_FOLDER_PATH + "RFC1213-MIB.txt");

        Root iso = new Root("iso", 1);
        MyNode org = new MyNode("org", 3, iso);
        iso.addChild(org);
        MyNode dod = new MyNode("dod", 6, org);
        org.addChild(dod);

        List<BaseNode> everyNode = new ArrayList<>();
        everyNode.addAll(Arrays.asList(dod, org, iso));

        List<DataType> dataTypes = new ArrayList<>();
        {
            dataTypes.add(new DataType("INTEGER", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.INTEGER, 2, -2147483648, 2147483647));
            dataTypes.add(new DataType("OCTET STRING", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 4, 0, 65535));
            dataTypes.add(new DataType("NULL", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.NULL, 5));
            dataTypes.add(new DataType("OBJECT IDENTIFIER", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OBJECT_IDENTIFIER, 6));
            dataTypes.add(new DataType("SEQUENCE", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.SEQUENCE, 16));
            dataTypes.add(new DataType("SEQUENCE OF", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.SEQUENCE_OF, 16));
            dataTypes.add(new DataType("DisplayString", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 4, 0, 255));
            dataTypes.add(new DataType("PhysAddress", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 4));
            dataTypes.add(new DataType("NetworkAddress", Visibility.APPLICATION, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 0, 0, 4));
        }
        List<String> listImports = new ArrayList<>();
        List<String> listObjectIdentifierElement = new ArrayList<>();
        List<String> listObjectTypeElement = new ArrayList<>();
        List<String> listNewObjectTypesText = new ArrayList<>();

        List<String> filesText = new ArrayList<>();
        filesText.add(mainText);

        ImportsProvider.recursionImports(listImports, filesText);

        ObjectTypeProvider.createObjectTypeList(listObjectTypeElement, filesText);
        ObjectIdentifierProvider.createObjectIdentifierList(listObjectIdentifierElement, filesText);
        NewDataTypeProvider.defineNewDataTypesStrings(listNewObjectTypesText, filesText);

        NewDataTypeProvider.createNewDataTypes(dataTypes, listNewObjectTypesText);
        ObjectIdentifierProvider.putObjectIdentifierToTree(everyNode, listObjectIdentifierElement);
        ObjectTypeProvider.putObjectTypeToTree(everyNode, listObjectTypeElement);

        dataTypes.forEach(System.out::println);

        List<DataType> coding = Ego.detectDataType(NodeExtractor.findNode(OIDSplitter.getOIDPath("1.3.6.1.2.1.4.22 15 ffffffff aa55aa55 2"), iso), dataTypes);
        List<String> valuesList = ValueExtractor.getValues("1.3.6.1.2.1.4.22 15 ffffffff aa55aa55 2");

        int offSet = 0;

        StringBuilder expectedDataNames = new StringBuilder("");
        if (coding.size() != 0 && coding.get(0).getBaseType() == BaseType.SEQUENCE_OF) {
            offSet = 1;
        }

        for (int i = offSet; i < coding.size(); i++) {
            expectedDataNames.append(coding.get(i).getNewTypeName());
            if (i != coding.size() - 1) {
                expectedDataNames.append(", ");
            }
        }

        if ((coding.size() == 0 || coding.size() == 1) && valuesList.size() == 0) {
            throw new IllegalArgumentException("Not passed any argument. Arguments: " + valuesList.size() + ".");
        }

        if (coding.size() == valuesList.size() || coding.size() - 1 == valuesList.size()) {
            ProperSizeValidator validator = ProperSizeValidator.valueOf(dataTypes);
            boolean valid = true;
            for (int i = 0; i < coding.size() - 1; i++) {
                DataType dataType = coding.get(i + offSet);
                String value = valuesList.get(i);

                switch (dataType.getBaseType()) {
                    case OCTET_STRING:
                        if (OctetStringCoder.isValidOctetString(value)) {
                            valid = valid & validator.isValidSize(value, dataType);
                        }
                        break;
                    case INTEGER:
                        valid = valid & validator.isValidSize(value, dataType);
                        break;
                    case OBJECT_IDENTIFIER:
                        valid = valid & validator.isValidSize(value, dataType);
                        break;
                    default:
                        valid = false;
                        break;
                }
            }

            StringBuilder sb = new StringBuilder("");
            if (valid) {
                for (int i = 0; i < coding.size() - 1; i++) {
                    DataType dataType = coding.get(i + offSet);
                    String value = valuesList.get(i);
                    StringBuilder innerBuilder = new StringBuilder("");

                    if (dataType.getCodingMethod().equals(CodingMethod.EXPLICIT)) {
                        innerBuilder.append(DataTypeCoder.code(dataType));
                        DataType baseType = new DataType(DataType.getBaseType(dataTypes, dataType.getBaseType()), dataType.getMinSize(), dataType.getMaxSize());
                        String baseTypeCodedExplicitArgument = ImplicitTypeCoder.code(baseType, value);
                        innerBuilder.append(SizeCoder.getCodedArgumentLength(baseTypeCodedExplicitArgument));
                        innerBuilder.append(baseTypeCodedExplicitArgument);
                    } else {
                        innerBuilder.append(DataTypeCoder.code(dataType));
                        innerBuilder.append(ImplicitTypeCoder.code(dataType, value));
                    }
                    sb.append(innerBuilder);
                }
            }

            if (offSet == 1) {
                DataType sequenceDataType = coding.get(0);
                String dataTypeCoded = DataTypeCoder.code(sequenceDataType);
                sb.insert(0, SizeCoder.getCodedArgumentLength(sb.toString()));
                sb.insert(0, dataTypeCoded);
            }

            System.out.println(sb.toString());
        } else {
            throw new IllegalArgumentException("Not valid number of arguments. Arguments: " + valuesList.size()
                    + "; Data types: " + (coding.size() - offSet) + ". "
                    + "Expected data: " + expectedDataNames.toString());
        }

        //Drawer.drawTree(iso, "|");
    }

}
