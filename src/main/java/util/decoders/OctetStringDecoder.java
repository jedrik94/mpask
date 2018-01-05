package util.decoders;

import java.util.BitSet;
import java.util.List;

class OctetStringDecoder {
    static String decode(List<BitSet> bytesList, int length) {
        StringBuilder sB = new StringBuilder("");
        for (int i = 0; i < length; i++) {
            if (bytesList.get(i).get(0, 8).equals(new BitSet())) {
                sB.append("00");
            } else {
                sB.append(Integer.toHexString(bytesList.get(i).get(0, 8).toByteArray()[0] & 0xff));
            }
        }
        return sB.toString();
    }
}
