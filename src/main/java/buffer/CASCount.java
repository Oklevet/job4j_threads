package buffer;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);
    int current;

    public void increment() {
        while(true) {
            current = count.get();
            if (count.compareAndSet(current, current + 1)) {
                return;
            }
        }
    }

    public int get() {
        return count.get();
    }
}
