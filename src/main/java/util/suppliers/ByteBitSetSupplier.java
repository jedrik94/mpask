package util.suppliers;

import java.util.BitSet;

public class ByteBitSetSupplier {
    public static BitSet hexStringToBitSet(String hexString) {
        String tmp = !hexString.matches("^0x(.*)") ? "0x" + hexString : hexString;
        return BitSet.valueOf(new long[]{Long.valueOf(tmp.substring(2), 16)});
    }
}
