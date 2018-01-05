package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OIDDecoderTest {
    private List<BitSet> data0, data1, data2, data3, data4, data5, data6;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        data0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x28}));
        data1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x2b}));
        data2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x2b}),
                BitSet.valueOf(new byte[]{(byte) 0x06}));
        data3 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x2b}),
                BitSet.valueOf(new byte[]{(byte) 0x06}),
                BitSet.valueOf(new byte[]{(byte) 0x01}),
                BitSet.valueOf(new byte[]{(byte) 0x04}));
        data4 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x2b}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}),
                BitSet.valueOf(new byte[]{(byte) 0x06}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        data5 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x85}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        data6 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x85}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
    }

    @Test
    void decode() {
        assertEquals("1", OIDDecoder.decode(data0, 1));
        assertEquals("1.3", OIDDecoder.decode(data1, 1));
        assertEquals("1.3.6", OIDDecoder.decode(data2, 2));
        assertEquals("1.3.6.1.4", OIDDecoder.decode(data3, 4));
        assertEquals("1.3.255.6.255", OIDDecoder.decode(data4, 6));
        assertEquals("1.727", OIDDecoder.decode(data5, 2));
        assertEquals("1.727.255", OIDDecoder.decode(data6, 4));
    }

}