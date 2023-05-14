package pool;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    private static int arrSumRow(int[] arr) {
        return Arrays.stream(arr).sum();
    }

    public static Sums[] getSumRowsCols(int[][] matrix) {
        int colSum = 0;
        Sums[] arr = new Sums[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                colSum += matrix[j][i];
            }
            arr[i] = new Sums(arrSumRow(matrix[i]), colSum);
            colSum = 0;
        }
        return arr;
    }

    public static CompletableFuture<Sums[]> getColsAsync(int[][] matrix) {
        return CompletableFuture.supplyAsync(
                () -> getSumRowsCols(matrix)
        );
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums[]> resArr = getColsAsync(matrix);
        return resArr.get();
    }
}
