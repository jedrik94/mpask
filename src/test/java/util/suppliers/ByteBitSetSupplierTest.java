package util.suppliers;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class ByteBitSetSupplierTest {
    @Test
    void hexStringToBitSet() {
        assertEquals(BitSet.valueOf(new byte[]{0x01}), ByteBitSetSupplier.hexStringToBitSet("0x01"));
        assertEquals(BitSet.valueOf(new byte[]{(byte) 0xa0}), ByteBitSetSupplier.hexStringToBitSet("0xa0"));
        assertEquals(BitSet.valueOf(new byte[]{(byte) 0xff}), ByteBitSetSupplier.hexStringToBitSet("ff"));
        assertEquals(BitSet.valueOf(new byte[]{0x00}), ByteBitSetSupplier.hexStringToBitSet("00"));
    }

    @Test
    void hasEightBitLength() {
        assertTrue(8 <= ByteBitSetSupplier.hexStringToBitSet("0xff").size());
        assertTrue(8 <= ByteBitSetSupplier.hexStringToBitSet("0xa0").size());
        assertTrue(8 <= ByteBitSetSupplier.hexStringToBitSet("0x01").size());
        assertTrue(false == ByteBitSetSupplier.hexStringToBitSet("0x00").get(7));
    }

}