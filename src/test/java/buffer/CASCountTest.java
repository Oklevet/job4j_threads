package buffer;

import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class CASCountTest {
    int count = 0;

    private static class ThreadAdd extends Thread {
        int quan;
        CASCount casCount;

        public ThreadAdd(CASCount casCount, int quan) {
            this.casCount = casCount;
            this.quan = quan;
        }

        @Override
        public void run() {
            for (int i = 0; i < quan; i++) {
                casCount.increment();
            }
        }
    }

    @Test
    public void when2Thr() throws InterruptedException {
        CASCount casCount = new CASCount();
        ThreadAdd th1 = new ThreadAdd(casCount, 3);
        ThreadAdd th2 = new ThreadAdd(casCount, 5);
        th1.start();
        th2.start();
        th1.join();
        th2.join();
        assertEquals(casCount.get(), 8);
    }

    @Test
    public void when3Thr() throws InterruptedException {
        CASCount casCount = new CASCount();
        ThreadAdd th1 = new ThreadAdd(casCount, 3);
        ThreadAdd th2 = new ThreadAdd(casCount, 5);
        ThreadAdd th3 = new ThreadAdd(casCount, 10);
        th1.start();
        th2.start();
        th3.start();
        th1.join();
        th2.join();
        th3.join();
        assertEquals(casCount.get(), 18);    }
}