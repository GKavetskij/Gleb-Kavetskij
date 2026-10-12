import java.util.Scanner;
// Для каждого числа из заданной последовательности целых чисел, найти количество цифр, меньших 5.
public class Task12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество чисел в последовательности: ");
        int size = scanner.nextInt();

        int[] sequence = new int[size];
        System.out.println("Введите " + size + " целых чисел:");
        for (int i = 0; i < size; i++) {
            sequence[i] = scanner.nextInt();
        }

        for (int n : sequence) {
            String s = String.valueOf(Math.abs(n));
            int count = 0;

            for (int i = 0; i < s.length(); i++) {
                int digit = s.charAt(i) - '0';
                if (digit < 5) {
                    count++;
                }
            }

            System.out.println("Число: " + n + " -> цифр меньше 5: " + count);
        }

        scanner.close();
    }
}
