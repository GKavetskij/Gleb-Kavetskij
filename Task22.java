import java.util.Scanner;

public class Task22 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество чисел в последовательности: ");
        int size = scanner.nextInt();

        int[] sequence = new int[size];
        System.out.println("Введите " + size + " натуральных чисел:");
        for (int i = 0; i < size; i++) {
            sequence[i] = scanner.nextInt();
        }

        for (int n : sequence) {
            String s = String.valueOf(Math.abs(n));

            int firstDigit = s.charAt(0) - '0';
            int lastDigit = s.charAt(s.length() - 1) - '0';

            long sum = factorial(firstDigit) + factorial(lastDigit);
            System.out.println("Число: " + n + " -> " + firstDigit + "! + " + lastDigit + "! = " + sum);
        }

        scanner.close();
    }

    static long factorial(int x) {
        long result = 1;
        for (int i = 2; i <= x; i++) {
            result *= i;
        }
        return result;
    }
}