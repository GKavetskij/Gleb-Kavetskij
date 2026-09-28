public class Task2 {

    public static int[][] shiftMatrix(int[][] src, int k, String direction) {
        int n = src.length;
        int m = src[0].length;
        int[][] res = new int[n][m];

        switch (direction.toLowerCase()) {
            case "вправо":
                k = ((k % m) + m) % m;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < m; j++)
                        res[i][(j + k) % m] = src[i][j];
                break;
            case "влево":
                k = ((k % m) + m) % m;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < m; j++)
                        res[i][(j - k + m) % m] = src[i][j];
                break;
            case "вниз":
                k = ((k % n) + n) % n;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < m; j++)
                        res[(i + k) % n][j] = src[i][j];
                break;
            case "вверх":
                k = ((k % n) + n) % n;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < m; j++)
                        res[(i - k + n) % n][j] = src[i][j];
                break;
            default:
                System.out.println("Неверное направление!");
                return src;
        }
        return res;
    }

    public static void main(String[] args) {

        int[][] a = MatrixUtils.createAndFillMatrix();

        System.out.println("\nИсходная матрица:");
        MatrixUtils.printMatrix(a);

        System.out.print("\nВведите количество позиций k: ");
        int k = MatrixUtils.sc.nextInt();
        MatrixUtils.sc.nextLine();
        System.out.print("Направление (вправо/влево/вверх/вниз): ");
        String dir = MatrixUtils.sc.nextLine();

        int[][] shifted = shiftMatrix(a, k, dir);
        System.out.println("\nМатрица после сдвига " + dir + " на " + k + " позиций:");
        MatrixUtils.printMatrix(shifted);
    }
}