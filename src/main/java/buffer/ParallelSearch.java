package buffer;

import io.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>();
        Thread prod = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        final Thread consumer = new Thread(
                () -> {
                        while (!Thread.currentThread().isInterrupted() || queue.size() > 0) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        prod.start();
        consumer.start();
        consumer.interrupt();
    }
}
