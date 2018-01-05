package util.coders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeCoderTest {
    private StringBuilder codedValueTest0, codedValueTest1, codedValueTest2, codedValueTest3;

    @BeforeEach
    void setUp() {
        codedValueTest0 = new StringBuilder("12");
        codedValueTest1 = new StringBuilder("1234");
        codedValueTest2 = new StringBuilder("");
        for (int i = 0; i < 128; i++) {
            codedValueTest2.append("55");
        }
        codedValueTest3 = new StringBuilder("");
        for (int i = 0; i < 64; i++) {
            codedValueTest3.append("55");
        }
    }

    @Test
    void getCodedArgumentLength() {
        assertEquals("01", SizeCoder.getCodedArgumentLength(codedValueTest0.toString()));
        assertEquals("02", SizeCoder.getCodedArgumentLength(codedValueTest1.toString()));
        assertEquals("8180", SizeCoder.getCodedArgumentLength(codedValueTest2.toString()));
        assertEquals("40", SizeCoder.getCodedArgumentLength(codedValueTest3.toString()));
    }
}