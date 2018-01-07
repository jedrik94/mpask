package util.decoders;

import common.CodingMethod;
import common.Visibility;
import mib.tree.DataType;
import util.suppliers.BitSetToIntSupplier;

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class DataTypeDecoder {
    static int numberOfUsedBitSets = 0;

    public static AbstractMap.SimpleEntry<Object, Integer> decode(List<BitSet> bytesList, List<DataType> dataTypes) {
        BitSet analyzedBitSet = bytesList.get(0);
        Visibility visibility;
        CodingMethod codingMethod;
        int ID;

        if (analyzedBitSet.get(7) && analyzedBitSet.get(6)) {
            visibility = Visibility.PRIVATE;
        } else if (analyzedBitSet.get(7) && !analyzedBitSet.get(6)) {
            visibility = Visibility.CONTEXT_SPECIFIC;
        } else if (!analyzedBitSet.get(7) && analyzedBitSet.get(6)) {
            visibility = Visibility.APPLICATION;
        } else {
            visibility = Visibility.UNIVERSAL;
        }

        if (analyzedBitSet.get(5)) {
            codingMethod = CodingMethod.EXPLICIT;
        } else {
            codingMethod = CodingMethod.IMPLICIT;
        }

        if (analyzedBitSet.get(0, 5).equals(BitSet.valueOf(new byte[]{0x1f}))) {
            ID = getExtendedTag(bytesList);
        } else {
            ID = BitSetToIntSupplier.convertBitSetToInt(analyzedBitSet.get(0, 5));
            numberOfUsedBitSets = 1;
        }

        return new AbstractMap.SimpleEntry<>(
                dataTypes.stream().filter(dT -> dT.getCodingMethod().equals(codingMethod))
                .filter(dT -> dT.getVisibility().equals(visibility))
                .filter(dT -> dT.getID() == ID)
                .findFirst()
                .orElse(null), numberOfUsedBitSets);
    }

    static int getExtendedTag(List<BitSet> bytesList) {
        BitSet bitSet = new BitSet();
        int lastIndexOfTagByte = 1;
        for (int i = 1; i < bytesList.size(); i++) {
            final BitSet bS = new BitSet();

            IntStream.concat(bitSet.stream().map(k -> k + 7), bytesList.get(i).get(0, 7).stream()).forEach(bS::set);
            bitSet = bS;

            if (!bytesList.get(i).get(7)) {
                lastIndexOfTagByte = i;
                break;
            }
        }

        numberOfUsedBitSets = lastIndexOfTagByte + 1;

        return BitSetToIntSupplier.convertBitSetToInt(bitSet);
    }
}
