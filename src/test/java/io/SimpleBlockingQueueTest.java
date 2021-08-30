package io;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            expect.add(sbq.poll());
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
                expect.add(sbq.poll());
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
            this.sbq.offer(new Random().nextInt(100));
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
            arr.forEach(this.sbq::offer);
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
}