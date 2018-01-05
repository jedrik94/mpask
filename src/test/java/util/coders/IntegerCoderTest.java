package util.coders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerCoderTest {

    @Test
    void code() {
        assertEquals("00", IntegerCoder.code(0));
        assertEquals("7f", IntegerCoder.code(127));
        assertEquals("0080", IntegerCoder.code(128));
        assertEquals("0100", IntegerCoder.code(256));
        assertEquals("80", IntegerCoder.code(-128));
        assertEquals("ff7f", IntegerCoder.code(-129));
    }

}