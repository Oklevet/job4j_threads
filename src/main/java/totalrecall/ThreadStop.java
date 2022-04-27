package totalrecall;

public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        th.start();
        Thread.sleep(1000);
        th.interrupt();
    }
}
