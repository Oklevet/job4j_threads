package totalrecall;

public class ConsoleProgressV2 implements Runnable {
    private final String[] process = new String[]{"\\", "\\|", "\\||/"};

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgressV2());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r Load: " + process[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (i == process.length) {
                i = 0;
            }
        }
    }
}
