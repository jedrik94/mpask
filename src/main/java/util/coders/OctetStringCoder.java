package util.coders;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

class OctetStringCoder {
    static boolean isValidOctetString(String s) {
        return !s.matches("(.*)[^0-9a-f](.*)");
    }

    @Deprecated
    static String code(String s){
        try {
            return String.format("%x", new BigInteger(1, s.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "0123456789";
    }
}