package util.decoders;

import util.suppliers.BitSetToIntSupplier;

import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

class OIDDecoder {
    static String decode(List<BitSet> bytesList, int length) {
        StringBuilder sB = new StringBuilder("");
        int codedOID, start = 0;
        BitSet bitSet = new BitSet();
        for (int i = 0; i < length; i++) {
            final BitSet bS = new BitSet();

            IntStream.concat(bitSet.stream().map(k -> k + 7), bytesList.get(i).get(0, 7).stream()).forEach(bS::set);
            bitSet = bS;

            if (!bytesList.get(i).get(7)) {
                codedOID = BitSetToIntSupplier.convertBitSetToInt(bitSet);
                bitSet = new BitSet();
                if (i == start) {
                    sB.append("1");
                    if (codedOID - 40 != 0) {
                        sB.append(".").append(codedOID - 40);
                    }
                } else {
                    sB.append(codedOID);
                }

                if (i != length - 1) {
                    sB.append('.');
                }
            } else {
                start++;
            }
        }
        return sB.toString();
    }
}
