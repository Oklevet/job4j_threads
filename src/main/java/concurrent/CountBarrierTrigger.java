package concurrent;

public class CountBarrierTrigger {
    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(3);
        Thread thread = new Thread(countBarrier::await);
        thread.start();
        for (int i = 0; i < 4000; i++) {
            countBarrier.count();
        }
    }
}
