package pool;

import io.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        Thread th = new TaskRun();
        for (int i = 0; i < size; i++) {
            threads.add(new TaskRun());
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class TaskRun extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Runnable job = tasks.poll();
                    System.out.println('2');
                    job.run();
                    System.out.println('1');
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void shutdown() {
        System.out.println("shutdown");
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool tp = new ThreadPool();
        for (int i = 0; i < 12; i++) {
            tp.work(new Task(i));
        }
        tp.shutdown();
    }
}
