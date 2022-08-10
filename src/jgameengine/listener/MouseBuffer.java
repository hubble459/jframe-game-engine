package jgameengine.listener;

import java.awt.event.MouseEvent;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

public class MouseBuffer implements Iterable<MouseEvent> {
    private final Queue<MouseEvent> buffer = new ArrayDeque<>();

    public synchronized void append(MouseEvent e) {
        buffer.add(e);
    }

    public synchronized MouseEvent consume() {
        return buffer.poll();
    }

    @Override
    public Iterator<MouseEvent> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return buffer.peek() != null;
            }

            @Override
            public MouseEvent next() {
                return consume();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super MouseEvent> action) {
        for (MouseEvent mouseEvent : this) {
            action.accept(mouseEvent);
        }
    }

    public Queue<MouseEvent> getBuffer() {
        return buffer;
    }
}
