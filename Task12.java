import java.util.Random;
import java.util.Scanner;
public class Task12 {


    public static int[][] removeRowsAndColsWithMax(int[][] src, int[] maxValue) {
        int n = src.length;
        int m = src[0].length;

        int max = src[0][0];
        for (int[] row : src)
            for (int val : row)
                if (val > max) max = val;
        maxValue[0] = max;
        boolean[] rowDel = new boolean[n];
        boolean[] colDel = new boolean[m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (src[i][j] == max) {
                    rowDel[i] = true;
                    colDel[j] = true;
                }

        int newN = 0, newM = 0;
        for (int i = 0; i < n; i++) if (!rowDel[i]) newN++;
        for (int j = 0; j < m; j++) if (!colDel[j]) newM++;

        if (newN == 0 || newM == 0) return new int[0][0];

        int[][] res = new int[newN][newM];
        int ri = 0;
        for (int i = 0; i < n; i++) {
            if (rowDel[i]) continue;
            int rj = 0;
            for (int j = 0; j < m; j++) {
                if (colDel[j]) continue;
                res[ri][rj++] = src[i][j];
            }
            ri++;
        }
        return res;
    }

    public static void main(String[] args) {

        int[][] b = MatrixUtils.createAndFillMatrix();

        System.out.println("\nИсходная матрица:");
        MatrixUtils.printMatrix(b);

        int[] maxVal = new int[1];
        int[][] trimmed = removeRowsAndColsWithMax(b, maxVal);

        System.out.println("\nМаксимальный элемент: " + maxVal[0]);

        if (trimmed.length == 0 || trimmed[0].length == 0) {
            System.out.println("После удаления матрица пуста.");
        } else {
            System.out.println("Матрица после удаления строк и столбцов с максимумом:");
            MatrixUtils.printMatrix(trimmed);
        }
    }
}