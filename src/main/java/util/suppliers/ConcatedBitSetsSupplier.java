package util.suppliers;

import java.util.BitSet;
import java.util.List;

public class ConcatedBitSetsSupplier {
    static public BitSet concat(List<BitSet> bitSetsToConcat) {
        int listSize = bitSetsToConcat.size();
        BitSet concatedBitSet = new BitSet(listSize * 8);

        for (int i = 0; i < listSize; i++) {
            final int finalI = i + 1;
            bitSetsToConcat.get(i).stream().forEach(s -> concatedBitSet.set(s + (listSize - finalI) * 8));
        }

        return concatedBitSet;
    }
}
