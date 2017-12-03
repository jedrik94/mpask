package util.coders;

import mib.tree.DataType;

import java.util.List;

public class DataTypeCoder {
    public static String code(DataType dT) {
        int visibility, codingMethod, tag;

        switch (dT.getVisibility()) {
            case APPLICATION:
                visibility = 64;
                break;
            case UNIVERSAL:
                visibility = 0;
                break;
            case PRIVATE:
                visibility = 192;
                break;
            case CONTEXT_SPECIFIC:
                visibility = 128;
                break;
            default:
                visibility = 128;
        }

        switch (dT.getCodingMethod()) {
            case IMPLICIT:
                codingMethod = 0;
                break;
            case EXPLICIT:
                codingMethod = 32;
                break;
            default:
                codingMethod = 0;
        }

        if (dT.getID() == 31)
            throw new ArithmeticException("Unable to have ID tag equal 31");

        tag = dT.getID() <= 30 ? dT.getID() : 31;

        int dataTypeByte = visibility + codingMethod + tag;

        StringBuilder result = new StringBuilder(Integer.toHexString(dataTypeByte));

        if (tag == 31)
            result.append(codeExtendedTag(dT.getID()));

        if (result.length() % 2 != 0)
            result.insert(0, "0");

        return result.toString().toLowerCase();
    }

    private static String codeExtendedTag(int tagID) {
        StringBuilder s = new StringBuilder(Integer.toBinaryString(tagID));

        int lengthDividedBy7 = s.toString().length() / 7;
        int length = s.length();

        for (int i = 0; i < lengthDividedBy7; i++) {
            s.insert(length - 7 * (i + 1), "0");
        }

        int lengthModulo8 = s.toString().length() % 8;

        if (lengthModulo8 != 0) {
            for (int i = 0; i < 8 - lengthModulo8; i++) {
                s.insert(0, "0");
            }
        }

        int lengthDividedBy8 = s.toString().length() / 8;

        for (int i = 0; i < lengthDividedBy8 - 1; i++) {
            s.replace(i * 8, i * 8 + 1, "1");
        }

        return Long.toHexString(Long.valueOf(s.toString(), 2));
    }
}