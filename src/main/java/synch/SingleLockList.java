package synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public SingleLockList() {
        this.list = new ArrayList<>();
    }

    public List<T> copy(List<T> list) {
        return new ArrayList<T>(list);
    }

    public void add(T value) {
        list.add(value);
    }

    public int size() {
        return list.size();
    }

    public T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}