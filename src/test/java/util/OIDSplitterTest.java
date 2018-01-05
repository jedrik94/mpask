package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OIDSplitterTest {

    static private List<Integer> expectedResult0, expectedResult1, expectedResult2;

    @BeforeEach
    static void setUp() {
        expectedResult0 = new ArrayList<>(Arrays.asList(1, 2, 34, 5, 6));
        expectedResult1 = new ArrayList<>(Arrays.asList(1, 3, 6, 1, 5, 4));
        expectedResult2 = new ArrayList<>(Arrays.asList(1, 6, 5, 114, 15));
    }

    @Test
    public void getOIDPath() {
        assertEquals(expectedResult0, OIDSplitter.getOIDPath("1.2.34.5.6 6"));
        assertEquals(expectedResult1, OIDSplitter.getOIDPath("1.3.6.1.5.4 Value"));
        assertEquals(expectedResult2, OIDSplitter.getOIDPath(" 1.6.5.114.15   adwe aasd   "));
    }



}