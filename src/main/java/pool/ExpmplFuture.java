package pool;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ExpmplFuture {
    public static void main(String[] args) throws Exception {
        Callable<String> t = () -> "FutureTask! " + Thread.currentThread().getName();
        FutureTask<String> f = new FutureTask<>(t);
        new Thread(f).start();
        System.out.println(f.get());
    }
}
