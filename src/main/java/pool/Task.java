package pool;

public class Task implements Runnable {
    int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task run with id " + id);
    }
}
