package pool.mailing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void emailTo(User user) {
        pool.submit(() -> send(String.format("Notification %s to email %s.", user.getUsername(), user.getEmail()),
                String.format("Add new event to %s", user.getUsername()), user.getEmail()));
    }

    public void send(String subject, String body, String email) { }
}