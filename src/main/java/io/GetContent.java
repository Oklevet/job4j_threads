package io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class GetContent implements Input {
    private final File file;

    public GetContent(File file) {
        this.file = file;
    }

    @Override
    public synchronized String input(Predicate condition) {
        String output = "";
        int data;
        try (InputStream i = new FileInputStream(String.valueOf(file))) {
            while ((data = i.read()) > 0) {
                if (condition.test(data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
