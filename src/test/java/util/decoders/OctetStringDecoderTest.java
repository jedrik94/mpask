package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OctetStringDecoderTest {
    private List<BitSet> data0, data1, data2;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        data0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0xa5}),
                BitSet.valueOf(new byte[]{(byte) 0x6f}),
                BitSet.valueOf(new byte[]{(byte) 0x99}));
        data1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x83}),
                BitSet.valueOf(new byte[]{(byte) 0x92}),
                BitSet.valueOf(new byte[]{(byte) 0x00}),
                BitSet.valueOf(new byte[]{(byte) 0xa4}));
        data2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x12}),
                BitSet.valueOf(new byte[]{(byte) 0xed}),
                BitSet.valueOf(new byte[]{(byte) 0xf4}),
                BitSet.valueOf(new byte[]{(byte) 0x88}),
                BitSet.valueOf(new byte[]{(byte) 0xff}));
    }

    @Test
    void decode() {
        assertEquals("a56f99", OctetStringDecoder.decode(data0, 3));
        assertEquals("839200a4", OctetStringDecoder.decode(data1, 4));
        assertEquals("12edf488ff", OctetStringDecoder.decode(data2, 5));
    }

}