package util.coders;

class SizeCoder {
    static String getCodedArgumentLength(String codedValue) {
        StringBuilder lengthStringBuilder = new StringBuilder("");
        StringBuilder fillOctetZero = new StringBuilder(codedValue);

        if (fillOctetZero.length() % 2 != 0)
            fillOctetZero.insert(0, "0");

        long lengthHex = fillOctetZero.length() / 2;
        if (lengthHex <= 127) {
            lengthStringBuilder.append(Long.toHexString(lengthHex));
            lengthStringBuilder.insert(0, (lengthStringBuilder.length() % 2 != 0) ? "0" : "");
        } else {
            long lengthOfLengthOctets = (long) Math.ceil(Long.toHexString(lengthHex).length() / 2);
            lengthStringBuilder.append(Long.toHexString(lengthOfLengthOctets + 128));
            lengthStringBuilder.append(Long.toHexString(lengthHex));
            lengthStringBuilder.insert(0, (lengthStringBuilder.length() % 2 != 0) ? "0" : "");
        }

        return lengthStringBuilder.toString();
    }
}
