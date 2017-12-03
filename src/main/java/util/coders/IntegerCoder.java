package util;public class IntegerCoder{private static java.lang.StringBuilder minimizeSizeCodedInt(java.lang.StringBuilder s) {
        int len = s.length();
        for (char thirdChar = '0'; thirdChar <= '7'; thirdChar++)
            if (s.charAt(0) == '0' && s.charAt(1) == '0' && s.charAt(2) == thirdChar) {
                s.delete(0, 2);
            }

        for (char thirdChar = '8'; thirdChar <= 'f'; thirdChar++)
            if (s.charAt(0) == 'f' && s.charAt(1) == 'f' && s.charAt(2) == thirdChar) {
                s.delete(0, 2);
            }

        if (len != s.length())
            s = minimizeSizeCodedInt(s);

        return s;
    }}