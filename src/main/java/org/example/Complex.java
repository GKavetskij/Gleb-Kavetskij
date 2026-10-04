package org.example;

public class Complex {
    double re, im;

    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    Complex add(Complex o) {
        return new Complex(re + o.re, im + o.im);
    }

    @Override
    public String toString() {
        if (im == 0) return String.valueOf(re);
        if (re == 0) return im + "i";
        return re + (im < 0 ? " - " : " + ") + Math.abs(im) + "i";
    }
}