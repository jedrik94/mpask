package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDecoderTest {
    private List<BitSet> data0, data1, data2, data3, data4, data5;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        data0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x00}));
        data1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x7f}));
        data2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x00}),
                BitSet.valueOf(new byte[]{(byte) 0x80}));
        data3 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x01}),
                BitSet.valueOf(new byte[]{(byte) 0x00}));
        data4 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x80}));
        data5 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0xff}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));

    }

    @Test
    void decode() {
        assertEquals("0", IntegerDecoder.decode(data0, 1));
        assertEquals("127", IntegerDecoder.decode(data1, 1));
        assertEquals("128", IntegerDecoder.decode(data2, 2));
        assertEquals("256", IntegerDecoder.decode(data3, 2));
        assertEquals("-128", IntegerDecoder.decode(data4, 1));
        assertEquals("-129", IntegerDecoder.decode(data5, 2));
    }

}