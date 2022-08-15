package totalrecall;
import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

public class WgetV2 implements Runnable {
    private final String url;
    private final String name;
    private final long speed;
    private final Instant start;

    public WgetV2(String url, String name, int speed, Instant start) {
        this.url = url;
        this.name = name;
        this.speed = speed * 1000L;
        this.start = start;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream())) {
            FileOutputStream fileOutputStream = new FileOutputStream(name);
            byte[] dataBuff = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuff, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuff, 0, bytesRead);
                long diffTime = Duration.between(start, Instant.now()).toMillis();
                if (speed > diffTime) {
                    Thread.sleep(diffTime);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * String for debug in console
     * System.out.println("Duration: " + Duration.between(start, Instant.now()).toMillis());
     * @param args input https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml 1 download_temp.xml
     */
    //
    public static void main(String[] args) throws InterruptedException {
        WgetArgValid argValid = new WgetArgValid(args);
        Instant start = Instant.now();
        String url = argValid.getUrl();
        int speed = argValid.getTime();
        String name = argValid.getFileName();
        Thread wget = new Thread(new WgetV2(url, name, speed, start));
        wget.start();
        wget.join();
    }
}
