package totalrecall;

import concurrent.User;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserStorage {
    HashMap<Integer, User> storage = new HashMap<>();
    AtomicInteger id = new AtomicInteger(0);

    public boolean add(User user) {
        storage.putIfAbsent(id.incrementAndGet(), new User(id.get(), user.getAmount()));
        return user.getAmount() == storage.get(id).getAmount();
    }

    public boolean update(User user) {
        if (storage.containsKey(user.getId())) {
            storage.replace(user.getId(), new User(user.getId(), user.getAmount()));
            return user.getAmount() == storage.get(id).getAmount();
        }
        return false;
    }

    public boolean delete(User user) {
        storage.remove(user.getId());
        return storage.containsKey(user.getId());
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean res = false;
        if (storage.containsKey(fromId)) {
            if (storage.get(fromId).getAmount() > amount) {
                res = update(new User(fromId, storage.get(fromId).getAmount() - amount));
                if (!res) {
                    return res;
                }
                update(new User(toId, storage.get(toId).getAmount() + amount));
            }
        }
        return res;
    }
}
