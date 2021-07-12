package concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wgets implements Runnable {
    private final String url;
    private final int speed;

    public Wgets(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        int speedKB = 4459;
        long pause = speedKB < speed ? speed - speedKB : 0;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(pause);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml"
    public static void main(String[] args) throws InterruptedException {
        long startT = System.currentTimeMillis();
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wgets(url, speed));
        wget.start();
        wget.join();
        long finishT = System.currentTimeMillis();
        System.out.println(finishT - startT + "ms");
    }
}