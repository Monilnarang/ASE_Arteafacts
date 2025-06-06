package org.eclipse.collections.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpreadFunctionsTest_Purified {

    @Test
    public void doubleSpreadOne_1() {
        assertEquals(-7831749829746778771L, SpreadFunctions.doubleSpreadOne(25.0d));
    }

    @Test
    public void doubleSpreadOne_2() {
        assertEquals(3020681792480265713L, SpreadFunctions.doubleSpreadOne(-25.0d));
    }

    @Test
    public void doubleSpreadTwo_1() {
        assertEquals(-7260239113076190123L, SpreadFunctions.doubleSpreadTwo(25.0d));
    }

    @Test
    public void doubleSpreadTwo_2() {
        assertEquals(-2923962723742798781L, SpreadFunctions.doubleSpreadTwo(-25.0d));
    }

    @Test
    public void longSpreadOne_1() {
        assertEquals(7972739338299824895L, SpreadFunctions.longSpreadOne(12345L));
    }

    @Test
    public void longSpreadOne_2() {
        assertEquals(5629574755565220972L, SpreadFunctions.longSpreadOne(23456L));
    }

    @Test
    public void longSpreadTwo_1() {
        assertEquals(-3823225069572514692L, SpreadFunctions.longSpreadTwo(12345L));
    }

    @Test
    public void longSpreadTwo_2() {
        assertEquals(7979914854381881740L, SpreadFunctions.longSpreadTwo(23456L));
    }

    @Test
    public void intSpreadOne_1() {
        assertEquals(-540084185L, SpreadFunctions.intSpreadOne(100));
    }

    @Test
    public void intSpreadOne_2() {
        assertEquals(1432552655L, SpreadFunctions.intSpreadOne(101));
    }

    @Test
    public void intSpreadTwo_1() {
        assertEquals(961801704L, SpreadFunctions.intSpreadTwo(100));
    }

    @Test
    public void intSpreadTwo_2() {
        assertEquals(662527578L, SpreadFunctions.intSpreadTwo(101));
    }

    @Test
    public void floatSpreadOne_1() {
        assertEquals(-1053442875L, SpreadFunctions.floatSpreadOne(9876.0F));
    }

    @Test
    public void floatSpreadOne_2() {
        assertEquals(-640291382L, SpreadFunctions.floatSpreadOne(-9876.0F));
    }

    @Test
    public void floatSpreadTwo_1() {
        assertEquals(-1971373820L, SpreadFunctions.floatSpreadTwo(9876.0F));
    }

    @Test
    public void floatSpreadTwo_2() {
        assertEquals(-1720924552L, SpreadFunctions.floatSpreadTwo(-9876.0F));
    }

    @Test
    public void shortSpreadOne_1() {
        assertEquals(-1526665035L, SpreadFunctions.shortSpreadOne((short) 123));
    }

    @Test
    public void shortSpreadOne_2() {
        assertEquals(-1120388305L, SpreadFunctions.shortSpreadOne((short) 234));
    }

    @Test
    public void shortSpreadTwo_1() {
        assertEquals(-474242978L, SpreadFunctions.shortSpreadTwo((short) 123));
    }

    @Test
    public void shortSpreadTwo_2() {
        assertEquals(-1572485272L, SpreadFunctions.shortSpreadTwo((short) 234));
    }
}
