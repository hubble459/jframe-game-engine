package jgameengine.listener;

import jgameengine.JGameOptions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DefaultKeyListener implements KeyListener {
    private final KeyBuffer buffer = new KeyBuffer();

    public KeyBuffer getBuffer() {
        return buffer;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        JGameOptions options = JGameOptions.getInstance();

        switch (e.getExtendedKeyCode()) {
            case KeyEvent.VK_F2:
                options.setPaused(!options.isPaused());
                break;
            case KeyEvent.VK_F3:
                options.setDebug(!options.isDebug());
                break;
            case KeyEvent.VK_F11:
                options.setFullscreen(!options.isFullscreen());
                break;
            case KeyEvent.VK_ESCAPE:
                if (options.isFullscreen()) {
                    options.setFullscreen(false);
                }
                break;
        }

        buffer.append(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
