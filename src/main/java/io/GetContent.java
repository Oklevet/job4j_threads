package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetContent implements Input<Integer> {
    private final File file;

    public GetContent(File file) {
        this.file = file;
    }

    @Override
    public synchronized String input(Predicate<Integer> condition) {
        StringBuilder output = new StringBuilder();
        int data;
        try (InputStream i = new FileInputStream(String.valueOf(file))) {
            while ((data = i.read()) > 0) {
                if (condition.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
