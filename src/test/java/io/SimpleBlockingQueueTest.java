package io;

import org.testng.annotations.Test;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {
    private final List<Integer> expect = new ArrayList<>();

    private class Consumer extends Thread {
        private final SimpleBlockingQueue<Integer> sbq;

        private Consumer(SimpleBlockingQueue sbq) {
            this.sbq = sbq;
        }

        @Override
        public void run() {
            try {
                expect.add(sbq.poll());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConsumerMass extends Thread {
        private final SimpleBlockingQueue<Integer> sbq;

        private ConsumerMass(SimpleBlockingQueue sbq) {
            this.sbq = sbq;
        }

        @Override
        public void run() {
            for (int i = 0; i < 6; i++) {
                try {
                    expect.add(sbq.poll());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Producer extends Thread {
        private final SimpleBlockingQueue sbq;

        private Producer(SimpleBlockingQueue sbq) {
            this.sbq = sbq;
        }

        @Override
        public void run() {
            try {
                this.sbq.offer(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ProducerMass extends Thread {
        private final SimpleBlockingQueue sbq;
        List<Integer> arr;

        private ProducerMass(SimpleBlockingQueue sbq, List<Integer> arr) {
            this.sbq = sbq;
            this.arr = arr;
        }

        @Override
        public void run() {
            for (Integer integer : arr) {
                try {
                    sbq.offer(integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void when1Produc() throws InterruptedException {
        SimpleBlockingQueue sbq = new SimpleBlockingQueue(10);
        Thread produc = new Producer(sbq);
        produc.start();
        produc.join();
        produc.join();
        System.out.println(sbq.size());
        assertThat(sbq.size(), is(1));
    }

    @Test
    public void when1Produc1Con() throws InterruptedException {
        SimpleBlockingQueue sbq = new SimpleBlockingQueue(10);
        Thread cons = new Consumer(sbq);
        Thread produc = new Producer(sbq);
        produc.start();
        cons.start();
        produc.join();
        produc.join();
        cons.join();
        System.out.println(sbq.size());
        assertThat(sbq.size(), is(0));
    }

    @Test
    public void when1ProducMass1Con() throws InterruptedException {
        List<Integer> arr = new ArrayList<>(List.of(1, 3, 5, 6, 7, 9));
        SimpleBlockingQueue sbq = new SimpleBlockingQueue(6);
        expect.clear();
        Thread cons = new ConsumerMass(sbq);
        Thread produc = new ProducerMass(sbq, arr);
        produc.start();
        cons.start();
        produc.join();
        cons.join();
        arr.forEach(System.out::print);
        System.out.println();
        expect.forEach(System.out::print);
        Collections.sort(arr);
        Collections.sort(expect);
        assertEquals(expect, arr);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                           Thread.currentThread().isInterrupted();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}