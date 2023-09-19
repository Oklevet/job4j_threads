package pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallIndexFinder<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T obj;
    private final int startPos;
    private final int endPos;

    public ParallIndexFinder(T[] array, T obj, int startPos, int endPos) {
        this.array = array;
        this.obj = obj;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    protected Integer compute() {
        if (obj == null) {
            return -1;
        }
        if (endPos - startPos < 11) {
            return findIndexInArr();
        }

        int midPos = (endPos - 1) / 2;
        ParallIndexFinder<T> firstFinder = new ParallIndexFinder<>(array, obj, startPos, midPos);
        ParallIndexFinder<T> secondFinder = new ParallIndexFinder<>(array, obj, midPos + 1, endPos);
        firstFinder.fork();
        secondFinder.fork();
        return Math.max(firstFinder.join(), secondFinder.join());
    }

    public int findIndexInArr() {
        for (int i = startPos; i <= endPos; i++) {
            if (array[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int sort(T[] array, T obj) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallIndexFinder<>(array, obj, 0, (array.length - 1)));
    }
}
