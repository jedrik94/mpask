package util.decoders;

import util.suppliers.BitSetToIntSupplier;
import util.suppliers.ConcatedBitSetsSupplier;

import java.util.BitSet;
import java.util.List;

class SizeDecoder {
    static int decode(List<BitSet> lBS) {
        BitSet sizeByte = lBS.get(0);
        int dataLength = 0;

        if (sizeByte.get(7)) {
            int kLength = BitSetToIntSupplier.convertBitSetToInt(sizeByte) - 128;
            dataLength = BitSetToIntSupplier.convertBitSetToInt(ConcatedBitSetsSupplier.concat(lBS.subList(1, 1 + kLength)));
        } else {
            dataLength = BitSetToIntSupplier.convertBitSetToInt(sizeByte);
        }

        return dataLength;
    }
}
