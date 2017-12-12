package util.coders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OctetStringCoderTest {
    @Test
    void isValidOctetString() {
        assertEquals(false, OctetStringCoder.isValidOctetString("aa55gg22"));
        assertEquals(true, OctetStringCoder.isValidOctetString("aa55ff11"));
    }

}