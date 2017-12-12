package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValueExtractorTest {

    static private List<String> expectedResult0, expectedResult1, expectedResult2, expectedResult3, expectedResult4, expectedResult5;

    @BeforeEach
    void setUp() {
        expectedResult0 = Arrays.asList("6", "\"Tomek\"", "26", "\"Andrzej ggg\"", "1.2.4.5.6");
        expectedResult1 = Arrays.asList("\"Value\"");
        expectedResult2 = Arrays.asList("\"adwe aasd\"");
        expectedResult3 = Arrays.asList("1231", "12", "123.44", "255.255.255.255");
        expectedResult4 = Arrays.asList("12", "ffff4455");
        expectedResult5 = Arrays.asList("15", "ffffffff", "aa55ga55", "2");
    }

    @Test
    void getValues() {
        assertEquals(expectedResult0, ValueExtractor.getValues("1.2.34.5.6 6 \"Tomek\" 26 \"Andrzej ggg\" 1.2.4.5.6"));
        assertEquals(expectedResult1, ValueExtractor.getValues("1.3.6.1.5.4 \"Value\""));
        assertEquals(expectedResult2, ValueExtractor.getValues(" 1.6.5.114.15   \"adwe aasd\"   "));
        assertEquals(expectedResult3, ValueExtractor.getValues(" 21.3.4.61 1231 12 123.44   255.255.255.255   "));
        assertEquals(expectedResult4, ValueExtractor.getValues("21.3.4.61 12 ffff4455"));
        assertEquals(expectedResult5, ValueExtractor.getValues("1.3.6.1.2.1.4.22 15 ffffffff aa55ga55 2"));
    }

}