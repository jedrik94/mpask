package util.decoders;

import common.BaseType;

import java.util.BitSet;
import java.util.List;

public class ContentDecoder {
    public static void decode(List<BitSet> bytesList, BaseType baseType, int length) {
        switch (baseType) {
            case NULL:
                break;
            case OBJECT_IDENTIFIER:
                break;
            case INTEGER:
                break;
            case OCTET_STRING:
                break;
            case SEQUENCE:
                break;
            case SEQUENCE_OF:
                break;
        }
    }
}
