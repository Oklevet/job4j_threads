package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            if (v.getVersion() != model.getVersion()) {
                try {
                    throw new OptimisticException("Versions re different");
                } catch (OptimisticException e) {
                    e.printStackTrace();
                }
            }
            memory.get(k).setName(model.getName());
            return model;
        }).equals(model);

    }

    public void delete(Base model) {
        memory.remove(model);
    }
}
