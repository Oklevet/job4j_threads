package pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static boolean isValid(int[][] matrix) {
        for (int[] mat : matrix) {
            if (matrix.length != mat.length) {
                return false;
            }
        }
        return true;
    }

    private static int arrSumRow(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    private static Sums[] getRows(int[][] matrix) {
        Sums[] arr = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            arr[i] = new Sums(arrSumRow(matrix[i]), 0);
        }
        return arr;
    }

    private static Sums[] getCols(int[][] matrix) {
        int colSum = 0;
        Sums[] arr = new Sums[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                colSum += matrix[j][i];
            }
            arr[i] = new Sums(1, colSum);
            colSum = 0;
        }
        return arr;
    }

    public static Sums[] sum(int[][] matrix) {
        if (!isValid(matrix)) {
            return new Sums[]{new Sums(-1, -1)};
        }
        Sums[] arr;
        Sums[] arrCols;
        arr = getRows(matrix);
        arrCols = getCols(matrix);
        for (int i = 0; i < arr.length; i++) {
            arr[i].setColSum(arrCols[i].getColSum());
        }
        return arr;
    }

    public static CompletableFuture<Sums[]> getColsAsync(int[][] matrix) {
        return CompletableFuture.supplyAsync(
                () -> getCols(matrix)
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        if (!isValid(matrix)) {
            return new Sums[]{new Sums(-1, -1)};
        }
        CompletableFuture<Sums[]> resArr = getColsAsync(matrix);
        Sums[] sums = getRows(matrix);
        for (int i = 0; i < sums.length; i++) {
            sums[i].setColSum(resArr.get()[i].getColSum());
        }
        return sums;
    }
}
