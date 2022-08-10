package jgameengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class DrawBuffer implements Iterable<JDrawable>, Cloneable {
    private final ArrayList<JDrawable> drawables = new ArrayList<>();

    public synchronized boolean add(JDrawable drawable) {
        synchronized (drawables) {
            return drawables.add(drawable);
        }
    }


    public synchronized boolean remove(JDrawable drawable) {
        synchronized (drawables) {
            return drawables.remove(drawable);
        }
    }

    @Override
    public synchronized Iterator<JDrawable> iterator() {
        return drawables.iterator();
    }

    @Override
    public synchronized void forEach(Consumer<? super JDrawable> action) {
        drawables.forEach(action);
    }

    public DrawBuffer copy() {
        try {
            return (DrawBuffer) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
