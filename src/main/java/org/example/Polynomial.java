package org.example;

import java.util.List;

public class Polynomial {
    Complex[] c; // c[i] — коэффициент при x^i

    Polynomial(Complex... c) {
        this.c = c;
    }

    static Polynomial sum(List<Polynomial> list) {
        int max = 0;
        for (Polynomial p : list) max = Math.max(max, p.c.length);

        Complex[] result = new Complex[max];
        for (int i = 0; i < max; i++) result[i] = new Complex(0, 0);

        for (Polynomial p : list)
            for (int i = 0; i < p.c.length; i++)
                result[i] = result[i].add(p.c[i]);

        return new Polynomial(result);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = c.length - 1; i >= 0; i--) {
            if (c[i].re == 0 && c[i].im == 0) continue;
            if (sb.length() > 0) sb.append(" + ");
            sb.append("(").append(c[i]).append(")");
            if (i > 0) sb.append("x^").append(i);
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}