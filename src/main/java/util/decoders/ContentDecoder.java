package util.decoders;

import common.BaseType;
import common.CodingMethod;
import mib.tree.DataType;

import java.util.*;

public class ContentDecoder {
    public static List<AbstractMap.SimpleEntry<DataType, String>> decode(List<BitSet> bytesList, List<DataType> dataTypes) {
        List<AbstractMap.SimpleEntry<DataType, String>> decodedData = new ArrayList<>();

        int usedBitSets = 0;
        int bitSetListSize = bytesList.size();
        int dataLength = 0;

        String dataTmp = "SEQUENCE";

        AbstractMap.SimpleEntry<Object, Integer> actualAnalyzedObject;
        BaseType actualAnalyzedObjectBaseType;
        CodingMethod actualAnalyzedObjectCodingMethod;
        DataType actualAnalyzedDataType;

        while (usedBitSets < bitSetListSize) {
            actualAnalyzedObject = DataTypeDecoder.decode(bytesList.subList(usedBitSets, bytesList.size()), dataTypes);
            actualAnalyzedObjectBaseType = ((DataType) actualAnalyzedObject.getKey()).getBaseType();
            actualAnalyzedObjectCodingMethod = ((DataType) actualAnalyzedObject.getKey()).getCodingMethod();
            actualAnalyzedDataType = (DataType) actualAnalyzedObject.getKey();
            usedBitSets += actualAnalyzedObject.getValue();

            actualAnalyzedObject = SizeDecoder.decode(bytesList.subList(usedBitSets, bytesList.size()));
            usedBitSets += actualAnalyzedObject.getValue();
            dataLength = (Integer) actualAnalyzedObject.getKey();

            if (actualAnalyzedObjectCodingMethod == CodingMethod.EXPLICIT) {
                actualAnalyzedObject = DataTypeDecoder.decode(bytesList.subList(usedBitSets, bytesList.size()), dataTypes);
                usedBitSets += actualAnalyzedObject.getValue();

                actualAnalyzedObject = SizeDecoder.decode(bytesList.subList(usedBitSets, bytesList.size()));
                usedBitSets += actualAnalyzedObject.getValue();
            }

            if (actualAnalyzedObjectBaseType != BaseType.SEQUENCE) {
                dataTmp = decodeDataValue(bytesList.subList(usedBitSets, bytesList.size()), actualAnalyzedObjectBaseType, dataLength);
                usedBitSets += dataLength;
            }

            decodedData.add(new AbstractMap.SimpleEntry<>(actualAnalyzedDataType, dataTmp));
        }

        return decodedData;
    }

    static String decodeDataValue(List<BitSet> bytesList, BaseType baseType, int length) {
        String result = "";

        switch (baseType) {
            case NULL:
                break;
            case OBJECT_IDENTIFIER:
                result = OIDDecoder.decode(bytesList, length);
                break;
            case INTEGER:
                result = IntegerDecoder.decode(bytesList, length);
                break;
            case OCTET_STRING:
                result = OctetStringDecoder.decode(bytesList, length);
                break;
            default:
                break;
        }
        return result;
    }
}
