package util.suppliers;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.List;

public class BitSetListFormHexFrameSupplier {
    public static List<BitSet> provide(String frame) {
        StringBuilder sB = new StringBuilder(frame);
        List<BitSet> bytesList = new LinkedList<BitSet>();

        for (int i = 0; i <= frame.length() - 2; i = i + 2) {
            bytesList.add(ByteBitSetSupplier.hexStringToBitSet(sB.subSequence(i, i + 2).toString()));
        }

        return bytesList;
    }
}