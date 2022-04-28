package buffer;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.push(6);
        stack.push(7);
        stack.push(8);
        stack.push(9);
        stack.push(10);
        stack.push(11);
        stack.push(12);
        stack.push(13);
        stack.push(14);
        stack.push(15);
        assertThat(stack.poll(), is(15));
        assertThat(stack.poll(), is(14));
        assertThat(stack.poll(), is(13));
        assertThat(stack.poll(), is(12));
        assertThat(stack.poll(), is(11));
        assertThat(stack.poll(), is(10));
        assertThat(stack.poll(), is(9));
        assertThat(stack.poll(), is(8));
        assertThat(stack.poll(), is(7));
        assertThat(stack.poll(), is(6));
        assertThat(stack.poll(), is(5));
        assertThat(stack.poll(), is(4));
        assertThat(stack.poll(), is(3));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertThat(stack.poll(), is(1));
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }
}