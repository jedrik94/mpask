package util.suppliers;

import java.util.BitSet;

public class BitSetToIntSupplier {
    public static int convertBitSetToInt(BitSet bitSet) {
        int ret = 0;
        for (int i = 0; i < bitSet.length(); i++) {
            ret += bitSet.get(i) ? (1 << i) : 0;
        }
        return ret;
    }
}