package pool.mailing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newCachedThreadPool();

    public void close() {
        pool.shutdown();
    }

    public void emailTo(User user) {
        pool.submit(() -> send(String.format("Notification %s to email %s.", user.getUsername(), user.getEmail()),
                String.format("Add new event to %s", user.getUsername()), user.getEmail()));
    }

    public void send(String subject, String body, String email) { }
}