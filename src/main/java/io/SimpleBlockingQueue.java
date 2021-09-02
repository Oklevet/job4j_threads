package io;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final Object monitor = this;
    private int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public SimpleBlockingQueue() {
    }

    public synchronized void offer(T value) {
        while (queue.size() == maxSize) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(value);
        monitor.notifyAll();
    }

    public synchronized T poll() {
        T temp;
        while (queue.size() == 0) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        temp = queue.poll();
        monitor.notifyAll();
        return temp;
    }

    public synchronized int size() {
        return queue.size();
    }
}
