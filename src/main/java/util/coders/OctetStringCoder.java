package util.coders;

public class OctetStringCoder {
    public static boolean isValidOctetString(String s) {
        return !s.matches("(.*)[^0-9a-f](.*)");
    }
}