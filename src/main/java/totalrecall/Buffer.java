package totalrecall;

public class Buffer {
    private final StringBuilder buffer = new StringBuilder();

    public void add(int value) {
        synchronized (this) {   // здесь должна быть синхронизация и value, и buffer.
            System.out.print(value);
            buffer.append(value);
        }
    }

    @Override
    public String toString() {
        synchronized (buffer) { // здесь вместо this можно синхронизировать buffer.
            return buffer.toString();
        }
    }
}
