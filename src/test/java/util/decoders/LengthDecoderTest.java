package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LengthDecoderTest {
    private List<BitSet> data0, data1, data2, data3, data4;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        data0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x82}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        data1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x83}),
                BitSet.valueOf(new byte[]{(byte) 0x92}),
                BitSet.valueOf(new byte[]{(byte) 0xa4}),
                BitSet.valueOf(new byte[]{(byte) 0x75}));
        data2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x84}),
                BitSet.valueOf(new byte[]{(byte) 0x06}),
                BitSet.valueOf(new byte[]{(byte) 0xb4}),
                BitSet.valueOf(new byte[]{(byte) 0xb0}),
                BitSet.valueOf(new byte[]{(byte) 0x61}));
        data3 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x7f}),
                BitSet.valueOf(new byte[]{(byte) 0x81}));
        data4 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x11}));
    }

    @Test
    void getExtendedDataLength() {
        assertEquals(33_151, LengthDecoder.getExtendedDataLength(data0));
        assertEquals(9_610_357, LengthDecoder.getExtendedDataLength(data1));
        assertEquals(112_504_929, LengthDecoder.getExtendedDataLength(data2));
    }

    @Test
    void getDataLength() {

        assertEquals(33_151, LengthDecoder.getDataLength(data0));
        assertEquals(9_610_357, LengthDecoder.getDataLength(data1));
        assertEquals(112_504_929, LengthDecoder.getDataLength(data2));
        assertEquals(127, LengthDecoder.getDataLength(data3));
        assertEquals(17, LengthDecoder.getDataLength(data4));
    }
}