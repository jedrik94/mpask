import common.BaseType;
import common.CodingMethod;
import common.Constants;
import common.Visibility;
import mib.tree.BaseNode;
import mib.tree.DataType;
import mib.tree.MyNode;
import mib.tree.Root;
import util.*;
import util.coders.DataTypeCoder;
import util.coders.IntegerCoder;
import util.providers.ImportsProvider;
import util.providers.NewDataTypeProvider;
import util.providers.ObjectIdentifierProvider;
import util.providers.ObjectTypeProvider;

import java.util.*;

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
        //everyNode.forEach(System.out::println);

        List<DataType> coding = Ego.detectDataType(NodeExtractor.findNode(OIDSplitter.getOIDPath("1.3.6.1.2.1.1.1 15"), iso), dataTypes);
        List<String> valuesList = ValueExtractor.getValues("1.3.6.1.2.1.1.1 15");

        for (Iterator<DataType> i0 = coding.iterator(); i0.hasNext(); ) {
            Iterator<String> i1 = valuesList.iterator();
            if (i1.hasNext()) {
                System.out.println(i0.next());
                System.out.println(i1.next());
            }
        }

        DataTypeCoder.code(coding.get(0));

        System.out.println(IntegerCoder.code(128));

        //Drawer.drawTree(iso, "|");


    }

}
