package totalrecall;

public class Buffer {
    private final StringBuilder buffer = new StringBuilder();

    /**
     * здесь должна быть синхронизация и value, и buffer.
     */
    public void add(int value) {
        synchronized (this) {
            System.out.print(value);
            buffer.append(value);
        }
    }

    /**
     * здесь вместо this можно синхронизировать buffer.
     */
    @Override
    public String toString() {
        synchronized (buffer) {
            return buffer.toString();
        }
    }
}
