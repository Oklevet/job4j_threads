package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions re different");
            }
            Base updBase = new Base(model.getId(), model.getVersion() + 1);
            updBase.setName(model.getName());
            return updBase;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public int size() {
        return memory.size();
    }

    public Base get(int index) {
        return memory.get(index);
    }
}
