package util.coders;

class IntegerCoder {
    private static StringBuilder minimizeSizeCodedInt(StringBuilder s) {

        int len = s.length();
        for (int thirdChar = 48; thirdChar <= 55; thirdChar++)
            if (s.charAt(0) == '0' && s.charAt(1) == '0' && s.charAt(2) == (char) thirdChar) {
                s.delete(0, 2);
            }

        for (int thirdChar = 56; thirdChar <= 102; thirdChar++) {
            if (thirdChar == 58) {
                thirdChar = 97;
            }
            if (s.charAt(0) == 'f' && s.charAt(1) == 'f' && s.charAt(2) == (char) thirdChar) {
                s.delete(0, 2);
            }
        }

        if (len != s.length())
            s = minimizeSizeCodedInt(s);

        return s;
    }

    static String code(int inputInteger) {
        if (inputInteger == 0) {
            return "00";
        }

        StringBuilder s = new StringBuilder(Integer.toHexString(inputInteger));

        if (s.length() < 8) {
            int length = 8 - s.length();
            for (int i = 0; i < length; i++)
                s.insert(0, "0");
        }

        return minimizeSizeCodedInt(s).toString().toLowerCase();
    }
}