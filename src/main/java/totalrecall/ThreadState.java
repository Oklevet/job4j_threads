package totalrecall;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread();
        Thread sec = new Thread();
        first.start();
        sec.start();
        while (first.getState() != Thread.State.TERMINATED || sec.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getName());
            System.out.println(sec.getName());
        }
        System.out.println(first.getState() + "1");
        System.out.println(sec.getState() + "2");
    }
}
