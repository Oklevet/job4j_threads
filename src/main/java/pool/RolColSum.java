package pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
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
        CompletableFuture<Sums[]> resArr = getColsAsync(matrix);
        Sums[] sums = getRows(matrix);
        for (int i = 0; i < sums.length; i++) {
            sums[i].setColSum(resArr.get()[i].getColSum());
        }
        return sums;
    }
}
