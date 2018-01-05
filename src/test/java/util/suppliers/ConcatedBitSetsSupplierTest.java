package util.suppliers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcatedBitSetsSupplierTest {
    List<BitSet> example0, example1, example2;

    @BeforeEach
    void setUp() {
        example0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x10}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        example1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0xff}),
                BitSet.valueOf(new byte[]{(byte) 0x11}));
        example2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x44}));
    }


    @Test
    void concat() {
        assertEquals(BitSet.valueOf(new byte[]{(byte) 0x7f, (byte) 0x81, (byte) 0x10}), ConcatedBitSetsSupplier.concat(example0));
        assertEquals(BitSet.valueOf(new byte[]{(byte) 0x11, (byte) 0xff}), ConcatedBitSetsSupplier.concat(example1));
        assertEquals(BitSet.valueOf(new byte[]{(byte) 0x44}), ConcatedBitSetsSupplier.concat(example2));
    }
}