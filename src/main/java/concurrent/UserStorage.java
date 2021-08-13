package concurrent;

public class UserStorage {
    public boolean add(User user) {
        return true;
    };

    public boolean update(User user) {
        return true;
    };

    public boolean delete(User user) {
        return true;
    };

    public void transfer(int fromId, int toId, int amount){};
}
