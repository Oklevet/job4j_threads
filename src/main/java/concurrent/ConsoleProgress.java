package concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(20000);
        progress.interrupt();
    }
    @Override
    public void run() {
        String[] process = new String[] {"\\", "\\|", "\\|/" };
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[i++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == process.length) {
                i = 0;
            }
        }
    }
}
