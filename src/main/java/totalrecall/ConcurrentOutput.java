package totalrecall;

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName() + "++++")
        );
        System.out.println(Thread.currentThread().getName() + "1");
        another.start();
        System.out.println(Thread.currentThread().getName() + "2");
        System.out.println(another.getName() + "3");
    }
}
