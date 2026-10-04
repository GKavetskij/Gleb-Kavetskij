package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {

    @Test
    void testSumTwoPolynomials() {
        Polynomial p1 = new Polynomial(new Complex(1, 0), new Complex(2, 0));
        Polynomial p2 = new Polynomial(new Complex(3, 0), new Complex(4, 0));

        Polynomial s = Polynomial.sum(List.of(p1, p2));

        assertEquals(4.0, s.c[0].re, 1e-9);
        assertEquals(6.0, s.c[1].re, 1e-9);
    }

    @Test
    void testSumDifferentDegrees() {
        Polynomial p1 = new Polynomial(new Complex(1, 0), new Complex(2, 0));
        Polynomial p2 = new Polynomial(new Complex(5, 0));

        Polynomial s = Polynomial.sum(List.of(p1, p2));

        assertEquals(2, s.c.length);
        assertEquals(6.0, s.c[0].re, 1e-9);
        assertEquals(2.0, s.c[1].re, 1e-9);
    }

    @Test
    void testSumComplexCoefficients() {
        Polynomial p1 = new Polynomial(new Complex(1, 1), new Complex(2, -1));
        Polynomial p2 = new Polynomial(new Complex(-1, 0), new Complex(0, 4));

        Polynomial s = Polynomial.sum(List.of(p1, p2));

        assertEquals(0.0, s.c[0].re, 1e-9);
        assertEquals(1.0, s.c[0].im, 1e-9);
        assertEquals(2.0, s.c[1].re, 1e-9);
        assertEquals(3.0, s.c[1].im, 1e-9);
    }

    @Test
    void testSumEmptyList() {
        Polynomial s = Polynomial.sum(List.of());
        assertEquals(1, s.c.length);
        assertEquals(0.0, s.c[0].re, 1e-9);
    }

    @Test
    void testToStringZero() {
        Polynomial zero = new Polynomial(new Complex(0, 0));
        assertEquals("0", zero.toString());
    }
}