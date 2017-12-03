package util.coders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectIdentifierCoderTest {

    private List<String> OIDList0, OIDList1, OIDList2, OIDList3;

    @BeforeEach
    void setUp() {
        OIDList0 = Arrays.asList("2", "100", "6", "65535", "2", "1", "1", "1");
        OIDList1 = Arrays.asList("1", "3", "12", "127", "3");
        OIDList2 = Arrays.asList("1", "4");
        OIDList3 = Arrays.asList("1");
    }

    @Test
    void code() {
        assertEquals("81340683ff7f02010101", ObjectIdentifierCoder.code(OIDList0));
        assertEquals("2b0c7f03", ObjectIdentifierCoder.code(OIDList1));
        assertEquals("2c", ObjectIdentifierCoder.code(OIDList2));
        assertEquals("28", ObjectIdentifierCoder.code(OIDList3));
    }

}