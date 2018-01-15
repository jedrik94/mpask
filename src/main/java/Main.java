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
import util.decoders.ContentDecoder;
import util.decoders.DataTypeDecoder;
import util.suppliers.*;

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
            dataTypes.add(new DataType("SEQUENCE", Visibility.UNIVERSAL, CodingMethod.EXPLICIT, BaseType.SEQUENCE, 16));
            dataTypes.add(new DataType("SEQUENCE OF", Visibility.UNIVERSAL, CodingMethod.EXPLICIT, BaseType.SEQUENCE_OF, 16));
            dataTypes.add(new DataType("DisplayString", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 4, 0, 255));
            dataTypes.add(new DataType("PhysAddress", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 4));
            dataTypes.add(new DataType("NetworkAddress", Visibility.APPLICATION, CodingMethod.IMPLICIT, BaseType.OCTET_STRING, 0, 0, 4));
            dataTypes.add(new DataType("GetRequestPDU", Visibility.PRIVATE, CodingMethod.IMPLICIT, BaseType.SEQUENCE, 0));
            dataTypes.add(new DataType("GetResponsePDU", Visibility.PRIVATE, CodingMethod.IMPLICIT, BaseType.SEQUENCE, 2));
        }
        List<String> listImports = new ArrayList<>();
        List<String> listObjectIdentifierElement = new ArrayList<>();
        List<String> listObjectTypeElement = new ArrayList<>();
        List<String> listNewObjectTypesText = new ArrayList<>();

        List<String> filesText = new ArrayList<>();
        filesText.add(mainText);

        ImportsSupplier.recursionImports(listImports, filesText);

        ObjectTypeSupplier.createObjectTypeList(listObjectTypeElement, filesText);
        ObjectIdentifierSupplier.createObjectIdentifierList(listObjectIdentifierElement, filesText);
        NewDataTypeSupplier.defineNewDataTypesStrings(listNewObjectTypesText, filesText);

        NewDataTypeSupplier.createNewDataTypes(dataTypes, listNewObjectTypesText);
        ObjectIdentifierSupplier.putObjectIdentifierToTree(everyNode, listObjectIdentifierElement);
        ObjectTypeSupplier.putObjectTypeToTree(everyNode, listObjectTypeElement);

        String userInputString = "1.3.6.1.2.1.4.22 15 aa55fa55 ffffffff 2"; // "1.3.6.1.2.1.1.7 100"

        List<DataType> coding = Ego.detectDataType(NodeExtractor.findNode(OIDSplitter.getOIDPath(userInputString), iso), dataTypes);
        List<String> valuesList = ValueExtractor.getValues(userInputString);

        BERCoder berCoder = new BERCoder(dataTypes, coding, valuesList);

        System.out.println(berCoder.createDataFrame());

        ContentDecoder.decode(BitSetListFormHexFrameSupplier.provide(berCoder.createDataFrame()), dataTypes).forEach(System.out::println);


        System.out.println(PDUFrameCreator.init(dataTypes,"0402ffff"));

        ContentDecoder.decode(BitSetListFormHexFrameSupplier.provide(PDUFrameCreator.init(dataTypes,"0402ffff")), dataTypes).forEach(System.out::println);
    }

}
