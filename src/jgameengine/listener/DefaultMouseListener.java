package jgameengine.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DefaultMouseListener extends MouseAdapter {
    private final MouseBuffer buffer = new MouseBuffer();

    public MouseBuffer getBuffer() {
        return buffer;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        buffer.append(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        buffer.append(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        buffer.append(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        buffer.append(e);
    }
}
