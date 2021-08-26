package concurrent;

import java.util.HashMap;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final HashMap<Integer, User> storage = new HashMap();
    @GuardedBy("this")
    private int id = 0;

    public synchronized boolean add(User user) {
        id++;
        storage.put(id, new User(id, user.getAmount()));
        return storage.get(id).getAmount() == user.getAmount();
    };

    public synchronized User get(int id) {
        return storage.get(id);
    };

    public synchronized boolean update(User user) {
        if (storage.containsKey(user.getId())) {
            storage.replace(user.getId(), new User(user.getId(), user.getAmount()));
            return storage.get(user.getId()).getAmount() == user.getAmount();
        }
        return false;
    };

    public synchronized boolean delete(User user) {
        if (storage.containsKey(user.getId())) {
            storage.remove(user.getId());
            return !storage.containsKey(user.getId());
        }
        return false;
    };

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (storage.containsKey(fromId) && storage.containsKey(toId) && storage.get(fromId).getAmount() > amount) {
            return update(new User(fromId, storage.get(fromId).getAmount() - amount))
                    && update(new User(toId, storage.get(toId).getAmount() + amount));
        }
        return false;
    }
}
