package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {

    @Test
    void testAdd() {
        Complex a = new Complex(1, 2);
        Complex b = new Complex(3, -5);
        Complex r = a.add(b);
        assertEquals(4.0, r.re, 1e-9);
        assertEquals(-3.0, r.im, 1e-9);
    }

    @Test
    void testToStringReal() {
        assertEquals("5.0", new Complex(5, 0).toString());
    }

    @Test
    void testToStringImaginary() {
        assertEquals("3.0i", new Complex(0, 3).toString());
    }

    @Test
    void testToStringBoth() {
        assertEquals("2.0 + 3.0i", new Complex(2, 3).toString());
    }

    @Test
    void testToStringNegativeIm() {
        assertEquals("2.0 - 3.0i", new Complex(2, -3).toString());
    }
}