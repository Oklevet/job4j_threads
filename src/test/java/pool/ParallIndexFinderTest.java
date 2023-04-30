package pool;

import concurrent.User;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static pool.ParallIndexFinder.sort;

public class ParallIndexFinderTest {
    @Test
    public void whenArrStrLess10() {
        String[] str = {"qwe", "asd", "zxc", "qwerty"};
        int expected = 2;
        int res = sort(str, "zxc");
        assertEquals(expected, res);
    }

    @Test
    public void whenArrStrMore10() {
        String[] str = {"qwe1", "qwe2", "qwe3", "qwe4", "qwe5",
                        "asd1", "asd2", "asd3", "asd4", "asd5",
                        "zxc1", "zxc2", "zxc3", "zxc4", "zxc5"};
        int expected = 10;
        int res = sort(str, "zxc1");
        assertEquals(expected, res);
    }

    @Test
    public void whenArrStrMore10FindObjIsNull() {
        String[] str = {"qwe1", "qwe2", "qwe3", "qwe4", "qwe5",
                "asd1", "asd2", "asd3", "asd4", "asd5",
                null, "zxc2", "zxc3", "zxc4", "zxc5"};
        int expected = -1;
        int res = sort(str, null);
        assertEquals(expected, res);
    }

    @Test
    public void whenArrIntMore10() {
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88};
        int expected = 6;
        int res = sort(nums, 7);
        assertEquals(expected, res);
    }

    @Test
    public void whenArrObjMore10() {
        User[] users = {new User(1, 100), new User(2, 100), new User(3, 100),
                new User(4, 100), new User(5, 100), new User(6, 100),
                new User(7, 100), new User(8, 100), new User(9, 100),
                new User(10, 100), new User(11, 100), new User(12, 100),
                new User(13, 100), new User(14, 100), new User(15, 100)};
        int expected = 10;
        int res = sort(users, new User(11, 100));
        assertEquals(expected, res);
    }

    @Test
    public void whenArrIntMore10IntNotFound() {
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88};
        int expected = -1;
        int res = sort(nums, 733);
        assertEquals(expected, res);
    }
}