package util.coders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectIdentifierCoderTest {

    private String OIDString0, OIDString1, OIDString2, OIDString3;

    @BeforeEach
    void setUp() {
        OIDString0 = "2.100.6.65535.2.1.1.1";
        OIDString1 = "1.3.12.127.3";
        OIDString2 = "1.4";
        OIDString3 = "1";
    }

    @Test
    void code() {
        assertEquals("81340683ff7f02010101", ObjectIdentifierCoder.code(OIDString0));
        assertEquals("2b0c7f03", ObjectIdentifierCoder.code(OIDString1));
        assertEquals("2c", ObjectIdentifierCoder.code(OIDString2));
        assertEquals("28", ObjectIdentifierCoder.code(OIDString3));
    }

}