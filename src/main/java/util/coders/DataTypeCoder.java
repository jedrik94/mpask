package util.coders;public class DataTypeCoder{private static void code(java.util.List<mib.tree.DataType> coding) {
        mib.tree.DataType dT = coding.get(0);

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
        }

        switch (dT.getCodingMethod()) {
            case IMPLICIT:
                codingMethod = 0;
                break;
            case EXPLICIT:
                codingMethod = 32;
                break;
        }

        if (dT.getID() == 31)
            throw new java.lang.ArithmeticException("Unable to have ID tag equal 31");

        tag = dT.getID() <= 30 ? dT.getID() : 31;

        java.lang.StringBuilder s = new java.lang.StringBuilder(java.lang.Integer.toBinaryString(2147483647));

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

        java.lang.System.out.println(s.toString());
        java.lang.System.out.println(java.lang.Long.toHexString(java.lang.Long.valueOf(s.toString(), 2)));
    }}