package concurrent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserStorageTest {
    private static class ThreadAdd1 extends Thread {
        private final UserStorage storage;

        public ThreadAdd1(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            this.storage.add(new User(1, 700));
        }
    }

    private static class ThreadAdd2 extends Thread {
        private final UserStorage storage;

        public ThreadAdd2(UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.storage.add(new User(2, 500));
        }
    }

    @Test
    public void WhenTh1TransferTh2() throws InterruptedException {
        UserStorage storage = new UserStorage();
        Thread one = new ThreadAdd1(storage);
        Thread two = new ThreadAdd2(storage);
        one.start();
        two.start();
        one.join();
        two.join();
        storage.transfer(1, 2, 500);
        assertEquals(200, storage.get(1).getAmount());
        assertEquals(1000, storage.get(2).getAmount());
    }
}