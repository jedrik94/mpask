package util.decoders;

import util.suppliers.BitSetToIntSupplier;
import util.suppliers.ConcatedBitSetsSupplier;

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.List;

class SizeDecoder {
    static AbstractMap.SimpleEntry<Object, Integer> decode(List<BitSet> lBS) {
        BitSet sizeByte = lBS.get(0);
        int dataLength = 0;
        int usedBitSets = 1;

        if (sizeByte.get(7)) {
            int kLength = BitSetToIntSupplier.convertBitSetToInt(sizeByte) - 128;
            dataLength = BitSetToIntSupplier.convertBitSetToInt(ConcatedBitSetsSupplier.concat(lBS.subList(1, 1 + kLength)));
            usedBitSets = kLength + 1;
        } else {
            dataLength = BitSetToIntSupplier.convertBitSetToInt(sizeByte);
        }

        return new AbstractMap.SimpleEntry<>(dataLength, usedBitSets);
    }
}
