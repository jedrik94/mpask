package util.coders;

class OctetStringCoder {
    static boolean isValidOctetString(String s) {
        return !s.matches("(.*)[^0-9a-f](.*)");
    }
}