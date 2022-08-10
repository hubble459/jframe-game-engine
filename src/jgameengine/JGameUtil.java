package jgameengine;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.function.Consumer;

public class JGameUtil {
    public static final int ANY_KEY = 14369;
    private static JGameEngine gameEngine;
    private static final JGameOptions options = JGameOptions.getInstance();

    protected static void init(JGameEngine gameEngine) {
        JGameUtil.gameEngine = gameEngine;
    }

    public static void write(Object text) {
        if (gameEngine != null) {
            gameEngine.addDrawable(new JTextDrawable(text));
        }
    }

    public static void undo(int amount) {
        if (gameEngine != null) {
            Queue<JDrawable> toRemove = new ArrayDeque<>();
            for (int i = 0; i < gameEngine.getDrawables().size(); i++) {
                JDrawable d = gameEngine.getDrawables().get(i);
                if (d instanceof JTextDrawable) {
                    toRemove.add(d);
                }
            }
            for (int i = 0; i < amount; i++) {
                JDrawable remove = toRemove.poll();
                if (remove != null) {
                    remove.remove();
                }
            }
        }
    }

    public static void undo() {
        undo(1);
    }

    public static void remove(JDrawable drawable) {
        if (gameEngine != null) {
            gameEngine.removeDrawable(drawable);
        }
    }

    public static void waitFor(int keyCode, Consumer<KeyEvent> action) {
        if (gameEngine != null) {
            options.setPaused(true);

            KeyListener kl = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == keyCode || keyCode == ANY_KEY) {
                        action.accept(e);
                        gameEngine.removeKeyListener(this);
                    }
                }
            };
            gameEngine.addKeyListener(kl);
        } else {
            action.accept(null);
        }
    }

    public static void mouse(Consumer<MouseEvent> mouseEventConsumer) {
        if (gameEngine != null) {
            gameEngine.getContentPane().addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mouseEventConsumer.accept(e);
                }
            });
        }
    }

    public static ArrayList<JDrawable> getDrawables() {
        return null;
    }

    public static <T> ArrayList<T> getDrawables(Class<T> cls) {
        ArrayList<T> drawables = new ArrayList<>();

        if (gameEngine != null) {
            for (JDrawable drawable : gameEngine.getDrawables()) {
                if (drawable.getClass() == cls) {
                    drawables.add((T) drawable);
                }
            }
        }

        return drawables;
    }

    public static Color randomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
