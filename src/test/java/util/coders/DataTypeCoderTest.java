package util.coders;

import common.BaseType;
import common.CodingMethod;
import common.Visibility;
import mib.tree.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTypeCoderTest {
    private DataType dataTypeTester0, dataTypeTester1, dataTypeTester2, dataTypeTester3;

    @BeforeEach
    void setUp() {
        dataTypeTester0 = new DataType("SEQUENCE OF", Visibility.APPLICATION, CodingMethod.IMPLICIT, BaseType.SEQUENCE_OF, 16);
        dataTypeTester1 = new DataType("PhysAddress", Visibility.PRIVATE, CodingMethod.EXPLICIT, BaseType.OCTET_STRING, 4);
        dataTypeTester2 = new DataType("IpAddress", Visibility.UNIVERSAL, CodingMethod.IMPLICIT, BaseType.INTEGER, 1400);
        dataTypeTester3 = new DataType("PhysAddress", Visibility.CONTEXT_SPECIFIC, CodingMethod.EXPLICIT, BaseType.OCTET_STRING, 255);
    }

    @Test
    void code() {
        assertEquals("50", DataTypeCoder.code(dataTypeTester0));
        assertEquals("e4", DataTypeCoder.code(dataTypeTester1));
        assertEquals("1f8a78", DataTypeCoder.code(dataTypeTester2));
        assertEquals("bf817f", DataTypeCoder.code(dataTypeTester3));
    }

}