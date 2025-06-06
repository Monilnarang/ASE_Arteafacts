package org.eclipse.collections.impl.stack.mutable;

import java.util.EmptyStackException;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.stack.StackIterableTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class MutableStackTestCase_Purified extends StackIterableTestCase {

    @Override
    protected abstract <T> MutableStack<T> newStackWith(T... elements);

    @Override
    protected abstract <T> MutableStack<T> newStackFromTopToBottom(T... elements);

    @Override
    protected abstract <T> MutableStack<T> newStackFromTopToBottom(Iterable<T> elements);

    @Override
    protected abstract <T> MutableStack<T> newStack(Iterable<T> elements);

    public void sumByInt() {
        MutableStack<Integer> values = this.newStackFromTopToBottom(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        MutableObjectLongMap<Integer> result = values.sumByInt(i -> i % 2, e -> e);
        assertEquals(25, result.get(1));
        assertEquals(30, result.get(0));
    }

    public void sumByFloat() {
        MutableStack<Integer> values = this.newStackFromTopToBottom(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        MutableObjectDoubleMap<Integer> result = values.sumByFloat(f -> f % 2, e -> e);
        assertEquals(25.0f, result.get(1), 0.0);
        assertEquals(30.0f, result.get(0), 0.0);
    }

    public void sumByLong() {
        MutableStack<Integer> values = this.newStackFromTopToBottom(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        MutableObjectLongMap<Integer> result = values.sumByLong(l -> l % 2, e -> e);
        assertEquals(25, result.get(1));
        assertEquals(30, result.get(0));
    }

    public void sumByDouble() {
        MutableStack<Integer> values = this.newStackFromTopToBottom(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        MutableObjectDoubleMap<Integer> result = values.sumByDouble(d -> d % 2, e -> e);
        assertEquals(25.0d, result.get(1), 0.0);
        assertEquals(30.0d, result.get(0), 0.0);
    }

    @Test
    public void testPushPopAndPeek_1_testMerged_1() {
        MutableStack<String> stack = this.newStackWith();
        stack.push("1");
        assertEquals("1", stack.peek());
        assertEquals(this.newStackWith("1"), stack);
        stack.push("2");
        assertEquals("2", stack.peek());
        assertEquals(this.newStackWith("1", "2"), stack);
        stack.push("3");
        assertEquals("3", stack.peek());
        assertEquals(this.newStackWith("1", "2", "3"), stack);
        assertEquals("2", stack.peekAt(1));
        assertEquals("3", stack.pop());
        assertEquals("2", stack.pop());
        assertEquals("1", stack.pop());
    }

    @Test
    public void testPushPopAndPeek_19() {
        MutableStack<Integer> stack7 = this.newStackFromTopToBottom(1, 2, 3, 4);
        assertEquals(ArrayStack.newStackFromTopToBottom(2, 1), stack7.pop(2, ArrayStack.newStack()));
    }

    @Test
    public void testPushPopAndPeek_15_testMerged_3() {
        MutableStack<Integer> stack3 = Stacks.mutable.ofReversed(1, 2, 3);
        assertEquals(this.newStackFromTopToBottom(1, 2, 3), stack3);
        MutableStack<Integer> stack4 = Stacks.mutable.ofAll(FastList.newListWith(1, 2, 3));
        MutableStack<Integer> stack5 = Stacks.mutable.ofAllReversed(FastList.newListWith(1, 2, 3));
        assertEquals(this.newStackFromTopToBottom(3, 2, 1), stack4);
        assertEquals(this.newStackFromTopToBottom(1, 2, 3), stack5);
        MutableStack<Integer> stack6 = this.newStackFromTopToBottom(1, 2, 3, 4);
        assertEquals(FastList.newListWith(1, 2), stack6.pop(2, FastList.newList()));
        MutableStack<Integer> stack8 = this.newStackFromTopToBottom(1, 2, 3, 4);
        Verify.assertIterableEmpty(stack8.pop(0));
        assertEquals(ArrayStack.newStackFromTopToBottom(1, 2, 3, 4), stack8);
        assertEquals(FastList.newList(), stack8.peek(0));
        MutableStack<Integer> stack9 = ArrayStack.newStack();
        assertEquals(FastList.newList(), stack9.pop(0));
        assertEquals(FastList.newList(), stack9.peek(0));
        assertEquals(FastList.newList(), stack9.pop(0, FastList.newList()));
        assertEquals(ArrayStack.newStack(), stack9.pop(0, ArrayStack.newStack()));
    }

    @Test
    public void testPushPopAndPeek_13_testMerged_4() {
        MutableStack<Integer> stack2 = this.newStackFromTopToBottom(5, 4, 3, 2, 1);
        stack2.pop(2);
        assertEquals(this.newStackFromTopToBottom(3, 2, 1), stack2);
        assertEquals(FastList.newListWith(3, 2), stack2.peek(2));
    }
}
