import java.util.Random;
import java.util.Scanner;

public class MatrixUtils {

    public static final Scanner sc = new Scanner(System.in);
    public static final Random rnd = new Random();

    public static int[][] createAndFillMatrix() {
        System.out.print("Введите количество строк n: ");
        int n = sc.nextInt();
        System.out.print("Введите количество столбцов m: ");
        int m = sc.nextInt();

        int[][] matrix = new int[n][m];

        System.out.println("\nВыберите способ заполнения:");
        System.out.println("1 — Случайными числами (0..10)");
        System.out.println("2 — Вручную с консоли");
        System.out.print("Ваш выбор: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            fillRandom(matrix);
        } else {
            fillManual(matrix);
        }
        return matrix;
    }

    public static void fillRandom(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = rnd.nextInt(11);
    }

    public static void fillManual(int[][] matrix) {
        System.out.println("Введите элементы матрицы построчно:");
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print("a[" + i + "][" + j + "] = ");
                matrix[i][j] = sc.nextInt();
            }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row)
                System.out.printf("%5d", val);
            System.out.println();
        }
    }
}