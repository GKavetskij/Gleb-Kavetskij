import java.util.Scanner;
//Определить, сколько раз каждая десятичная цифра встречается в записи каждого
числа n с чётным количеством цифр из заданной последовательности натуральных чисел.

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество чисел в последовательности: ");
        int size = scanner.nextInt();

        int[] sequence = new int[size];
        System.out.println("Введите " + size + " целых чисел:");
        for (int i = 0; i < size; i++) {
            sequence[i] = scanner.nextInt();
        }

        boolean found = false;

        for (int n : sequence) {
            String s = String.valueOf(Math.abs(n));
            if (s.length() % 2 != 0) continue;

            found = true;
            int[] counts = new int[10];
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    counts[c - '0']++;
                }
            }
            System.out.println("Число: " + n + " (цифр: " + s.length() + ")");
            for (int d = 0; d < 10; d++) {
                if (counts[d] > 0) {
                    System.out.println("  цифра " + d + " встречается " + counts[d] + " раз");
                }
            }
        }
        if (!found) {
            System.out.println("В последовательности нет чисел с чётным количеством цифр.");
        }

        scanner.close();
    }
}
