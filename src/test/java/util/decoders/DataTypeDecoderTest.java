package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataTypeDecoderTest {
    private List<BitSet> data0, data1, data2;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        data0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x10}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        data1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x63}),
                BitSet.valueOf(new byte[]{(byte) 0x92}),
                BitSet.valueOf(new byte[]{(byte) 0xa4}),
                BitSet.valueOf(new byte[]{(byte) 0x75}));
        data2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x51}),
                BitSet.valueOf(new byte[]{(byte) 0xa6}),
                BitSet.valueOf(new byte[]{(byte) 0xb4}),
                BitSet.valueOf(new byte[]{(byte) 0xb0}),
                BitSet.valueOf(new byte[]{(byte) 0x61}));
    }

    @Test
    void getExtendedTag() throws InvocationTargetException, IllegalAccessException {
        assertEquals(255, DataTypeDecoder.getExtendedTag(data0));
        assertEquals(299_637, DataTypeDecoder.getExtendedTag(data1));
        assertEquals(80_549_985, DataTypeDecoder.getExtendedTag(data2));
    }
}