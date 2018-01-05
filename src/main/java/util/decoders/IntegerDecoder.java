package util.decoders;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.List;

class IntegerDecoder {
    static String decode(List<BitSet> bytesList, int length) {
        byte[] array = new byte[length];

        for (int i = 0; i < length; i++) {
            if (bytesList.get(i).get(0, 8).equals(new BitSet())) {
                array[i] = 0x00;
            } else {
                array[i] = bytesList.get(i).get(0, 8).toByteArray()[0];
            }
        }
        return String.valueOf(new BigInteger(array).intValue());
    }
}
