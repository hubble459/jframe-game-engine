package jgameengine.listener;

import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

public class KeyBuffer implements Iterable<KeyEvent> {
    private final Queue<KeyEvent> buffer = new ArrayDeque<>();

    public synchronized void append(KeyEvent e) {
        buffer.add(e);
    }

    public synchronized KeyEvent consume() {
        return buffer.poll();
    }

    @Override
    public Iterator<KeyEvent> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return buffer.peek() != null;
            }

            @Override
            public KeyEvent next() {
                return consume();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super KeyEvent> action) {
        for (KeyEvent keyEvent : this) {
            action.accept(keyEvent);
        }
    }

    public Queue<KeyEvent> getBuffer() {
        return buffer;
    }
}
