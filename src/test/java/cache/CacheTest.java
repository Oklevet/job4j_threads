package cache;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CacheTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void whenAdd() {
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        Cache cache = new Cache();
        cache.add(base1);
        assertThat(cache.size(), is(1));
    }

    @Test
    public void whenAdd2andDel() {
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        Cache cache = new Cache();
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.size(), is(2));
        cache.delete(base1);
        assertThat(cache.size(), is(1));
    }

    @Test
    public void whenUpdate() {
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 2, "qwe");
        Base base3 = new Base(2, 2, "asd");
        Cache cache = new Cache();
        cache.add(base1);
        cache.add(base2);
        cache.update(base3);
        assertThat(cache.get(2).getName(), is("asd"));
    }

    @Test
    void whenAddandUpdToExcep() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        Base wrongVers = new Base(1, 1);
        assertThatThrownBy(() -> cache.update(wrongVers))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions re different");
    }
}