package threads.threadlocal;

public class ThreadLocalDemo {
    public static ThreadLocal<String> tl = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        tl.set("This is main thread");
        System.out.println(tl.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
