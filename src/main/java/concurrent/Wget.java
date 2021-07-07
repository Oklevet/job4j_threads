package concurrent;

public class Wget {
    public static void main(String[] args) throws InterruptedException {
        Thread tr = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 101; i++) {
                            System.out.print("\rLoading : " + i  + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        tr.start();
        Thread.sleep(1000);
        tr.interrupt();
        tr.join();
    }
}
