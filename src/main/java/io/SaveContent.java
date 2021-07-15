package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SaveContent implements Output {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    @Override
    public void output(String content) {
        try (OutputStream o = new FileOutputStream(String.valueOf(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
