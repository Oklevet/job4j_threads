package concurrent;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized void count() {
        count++;
        monitor.notifyAll();
    }

    public void await() {
        while (count < total) {
            System.out.println("Thr waiting. Count = " + count);
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thr in await not wait now. Count = " + count);
    }
}
