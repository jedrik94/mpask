package util.coders;

import java.util.Arrays;
import java.util.List;

public class ObjectIdentifierCoder {
    public static String code(String objectIdentifierString) {
        List<String> objectIdentifierList = Arrays.asList(objectIdentifierString.split("\\."));

        int firstTwoOIDs = 0;
        if (objectIdentifierList.size() == 1) {
            firstTwoOIDs = 40 * Integer.parseInt(objectIdentifierList.get(0));
        } else {
            firstTwoOIDs = 40 * Integer.parseInt(objectIdentifierList.get(0)) + Integer.parseInt(objectIdentifierList.get(1));
        }

        String binaryRepresentationFirstTwoOIDs = codeOID(firstTwoOIDs);

        StringBuilder sB = new StringBuilder(getProperHexRepresentation(binaryRepresentationFirstTwoOIDs));

        for (int i = 2; i < objectIdentifierList.size(); i++) {
            sB.append(getProperHexRepresentation(codeOID(Integer.parseInt(objectIdentifierList.get(i)))));
        }
        return sB.toString();
    }

    private static String codeOID(int OID) {
        StringBuilder binaryRepresentationOIDs = new StringBuilder(Integer.toBinaryString(OID));
        int lengthBinaryRepresentationOIDs = binaryRepresentationOIDs.length();
        int lengthBinaryRepresentationOIDsDividedBy7 = lengthBinaryRepresentationOIDs / 7;

        if (lengthBinaryRepresentationOIDs >= 8) {
            for (int i = 0; i < lengthBinaryRepresentationOIDsDividedBy7; i++) {
                binaryRepresentationOIDs.insert(lengthBinaryRepresentationOIDs - 7 * (i + 1), "0");
            }

            int binaryRepresentationOIDsModulo8 = binaryRepresentationOIDs.length() % 8;
            for (int i = 0; i < 8 - binaryRepresentationOIDsModulo8; i++) {
                binaryRepresentationOIDs.insert(0, "0");
            }

            int lengthBinaryRepresentationOIDsDividedBy8 = binaryRepresentationOIDs.length() / 8;

            for (int i = 0; i < lengthBinaryRepresentationOIDsDividedBy8 - 1; i++) {
                binaryRepresentationOIDs.replace(i * 8, i * 8 + 1, "1");
            }
        }
        return binaryRepresentationOIDs.toString().toLowerCase();
    }

    private static String getProperHexRepresentation(String s) {
        StringBuilder stringBuilder = new StringBuilder(Long.toHexString(Long.valueOf(s, 2)));
        int length = stringBuilder.length();
        if (length % 2 != 0) {
            stringBuilder.insert(0, "0");
        }
        return stringBuilder.toString();
    }
}