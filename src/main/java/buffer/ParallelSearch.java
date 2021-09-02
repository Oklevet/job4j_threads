package buffer;

import io.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        Thread prod = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        final Thread consumer = new Thread(
                () -> {
                    while (prod.isAlive() || queue.size() > 0) {
                        System.out.println(queue.poll());
                    }
                }
        );
        prod.start();
        consumer.start();
        consumer.interrupt();
    }
}
