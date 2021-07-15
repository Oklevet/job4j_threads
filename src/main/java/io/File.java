package io;

public class File {
    private final File file;

    public File(final File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }
}
