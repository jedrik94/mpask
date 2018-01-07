package util.decoders;

import util.suppliers.BitSetToIntSupplier;

import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

@Deprecated
class LengthDecoder {
    static int getDataLength(List<BitSet> bytesList) {
        int length = 0;
        if (bytesList.get(0).get(7)) {
            length = getExtendedDataLength(bytesList);
        } else {
            length = BitSetToIntSupplier.convertBitSetToInt(bytesList.get(0).get(0, 7));
        }

        return length;
    }

    static int getExtendedDataLength(List<BitSet> bytesList) {
        int numberOfBytes = BitSetToIntSupplier.convertBitSetToInt(bytesList.get(0).get(0, 7));
        BitSet bitSet = new BitSet();

        for (int i = 1; i <= numberOfBytes; i++) {
            final BitSet bS = new BitSet();

            IntStream.concat(bitSet.stream().map(k -> k + 8), bytesList.get(i).get(0, 8).stream()).forEach(bS::set);
            bitSet = bS;
        }

        return BitSetToIntSupplier.convertBitSetToInt(bitSet);
    }
}
