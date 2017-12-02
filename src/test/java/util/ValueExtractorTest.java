package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueExtractorTest {

    static private String expectedResult0, expectedResult1, expectedResult2;

    @BeforeEach
    void setUp() {
        expectedResult0 = "6";
        expectedResult1 = "Value";
        expectedResult2 = "adwe aasd";
    }

    @Test
    void getValue() {
        assertEquals(expectedResult0, ValueExtractor.getValue("1.2.34.5.6 6"));
        assertEquals(expectedResult1, ValueExtractor.getValue("1.3.6.1.5.4 Value"));
        assertEquals(expectedResult2, ValueExtractor.getValue(" 1.6.5.114.15   adwe aasd   "));
    }

}