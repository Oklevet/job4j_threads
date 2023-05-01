package pool;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pool.RolColSum.*;

public class RolColSumTest {
    @Test
    public void whenMatrix1Sync() {
        int[] arr1 = {1};
        int[][] matrix = {arr1};
        Sums[] sums = sum(matrix);
        Sums[] expected = {new Sums(1, 1)};
        assertThat(sums, is(expected));
    }

    @Test
    public void whenMatrix4Sync() {
        int[] arr1 = {1, 1, 1, 1};
        int[] arr2 = {2, 2, 2, 2};
        int[] arr3 = {3, 3, 3, 3};
        int[] arr4 = {4, 4, 4, 4};
        int[][] matrix = {arr1, arr2, arr3, arr4};
        Sums[] sums = sum(matrix);
        Sums[] expected = {new Sums(4, 10), new Sums(8, 10),
                new Sums(12, 10), new Sums(16, 10)};
        assertThat(sums, is(expected));
    }

    @Test
    public void whenMatrix4Async() throws ExecutionException, InterruptedException {
        int[] arr1 = {1, 1, 1, 1};
        int[] arr2 = {2, 2, 2, 2};
        int[] arr3 = {3, 3, 3, 3};
        int[] arr4 = {4, 4, 4, 4};
        int[][] matrix = {arr1, arr2, arr3, arr4};
        Sums[] sums = asyncSum(matrix);
        Sums[] expected = {new Sums(4, 10), new Sums(8, 10),
                new Sums(12, 10), new Sums(16, 10)};
        assertThat(sums, is(expected));
    }

    @Test
    public void whenMatrixUnsquareAsync() throws ExecutionException, InterruptedException {
        int[] arr1 = {1, 1, 1};
        int[] arr2 = {2, 2, 2};
        int[] arr3 = {3, 3, 3};
        int[] arr4 = {4, 4, 4};
        int[][] matrix = {arr1, arr2, arr3, arr4};
        Sums[] sums = asyncSum(matrix);
        Sums[] expected = {new Sums(-1, -1)};
        assertThat(sums, is(expected));
    }

    @Test
    public void whenMatrixUnsquareSync() throws ExecutionException, InterruptedException {
        int[] arr1 = {1, 1, 1};
        int[] arr2 = {2, 2, 2};
        int[] arr3 = {3, 3, 3};
        int[] arr4 = {4, 4, 4};
        int[][] matrix = {arr1, arr2, arr3, arr4};
        Sums[] sums = sum(matrix);
        Sums[] expected = {new Sums(-1, -1)};
        assertThat(sums, is(expected));
    }
}