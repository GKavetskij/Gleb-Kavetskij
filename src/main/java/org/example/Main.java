package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите количество полиномов m: ");
        int m = sc.nextInt();

        List<Polynomial> polys = new ArrayList<>();


        for (int k = 0; k < m; k++) {
            System.out.println("\nПолином №" + (k + 1) );
            System.out.print("Введите степень полинома n: ");
            int n = sc.nextInt();

            Complex[] coeffs = new Complex[n + 1];
            for (int i = 0; i <= n; i++) {
                System.out.print("  Коэффициент при x^" + i
                        + " (сначала действительная часть, затем мнимая): ");
                double re = sc.nextDouble();
                double im = sc.nextDouble();
                coeffs[i] = new Complex(re, im);
            }
            polys.add(new Polynomial(coeffs));
        }


        System.out.println("\n Введённые полиномы: ");
        for (int i = 0; i < polys.size(); i++) {
            System.out.println("P" + (i + 1) + " = " + polys.get(i));
        }


        Polynomial sum = Polynomial.sum(polys);
        System.out.println("\nСумма всех полиномов:  ");
        System.out.println("S = " + sum);

        sc.close();
    }
}
