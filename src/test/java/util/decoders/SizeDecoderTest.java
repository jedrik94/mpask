package util.decoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SizeDecoderTest {
    private List<BitSet> example0, example1, example2, example3;

    @BeforeEach
    void setUp() {
        example0 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
        example1 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x7f}),
                BitSet.valueOf(new byte[]{(byte) 0x11}));
        example2 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x44}));
        example3 = Arrays.asList(BitSet.valueOf(new byte[]{(byte) 0x82}),
                BitSet.valueOf(new byte[]{(byte) 0x81}),
                BitSet.valueOf(new byte[]{(byte) 0x7f}));
    }

    @Test
    void decode() {
        assertEquals(129, ((Integer) SizeDecoder.decode(example0).getKey()).intValue());
        assertEquals(2, SizeDecoder.decode(example0).getValue().intValue());

        assertEquals(127, ((Integer) SizeDecoder.decode(example1).getKey()).intValue());
        assertEquals(1, SizeDecoder.decode(example1).getValue().intValue());

        assertEquals(68, ((Integer) SizeDecoder.decode(example2).getKey()).intValue());
        assertEquals(1, SizeDecoder.decode(example2).getValue().intValue());

        assertEquals(33151, ((Integer) SizeDecoder.decode(example3).getKey()).intValue());
        assertEquals(3, SizeDecoder.decode(example3).getValue().intValue());
    }
}