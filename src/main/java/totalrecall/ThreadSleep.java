package totalrecall;

public class ThreadSleep {
    public static void main(String[] args) {
        Thread th = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ...");
                        Thread.sleep(3000);
                        System.out.println("Loaded");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        th.start();
        System.out.println("Main");
    }
}
