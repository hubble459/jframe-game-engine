package jgameengine;

import jgameengine.listener.DefaultKeyListener;
import jgameengine.listener.DefaultMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Queue;

public class JGameCanvas extends JPanel {
    public static Font DEFAULT_FONT = new Font("Monospaced", Font.BOLD, 16);
    private final ArrayList<JDrawable> drawables;
    private final JGameOptions options = JGameOptions.getInstance();
    private final DefaultKeyListener keyListener = options.getKeyListener();
    private final DefaultMouseListener mouseListener = options.getMouseListener();
    private JDrawable hovering;

    protected JGameCanvas(ArrayList<JDrawable> drawables) {
        this.drawables = drawables;

        addMouseListener(mouseListener);
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                options.setMovingMouse(e);
            }
        });
    }

    public BufferedImage drawOnBuffer() {
        BufferedImage bImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImg.createGraphics();

        g2d.setColor(options.getBackgroundColor());

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0, 0, getParent().getWidth(), getParent().getHeight());

        synchronized (drawables) {
            Queue<KeyEvent> keys = keyListener.getBuffer().getBuffer();
            Queue<MouseEvent> clicks = mouseListener.getBuffer().getBuffer();

            ArrayList<JTextDrawable> textDrawables = new ArrayList<>();
            for (JDrawable jDrawable : drawables) {
                if (jDrawable instanceof JTextDrawable) {
                    textDrawables.add((JTextDrawable) jDrawable);
                }
            }

            drawTextDrawables(g2d, textDrawables);

            for (JDrawable drawable : drawables) {
                if (!(drawable instanceof JTextDrawable)) {
                    drawable.draw(g2d);
                    keys.forEach(drawable::onKeyPress);
                    MouseEvent movingMouse = options.getMovingMouse();
                    if (movingMouse != null && drawable.getShape().contains(movingMouse.getPoint())) {
                        drawable.onHover(movingMouse);

                        hovering = drawable;
                    }
                    for (MouseEvent mouseEvent : clicks) {
                        if (mouseEvent.getButton() == MouseEvent.BUTTON1
                                && drawable.getShape().contains(mouseEvent.getPoint())) {
                            drawable.onClick(mouseEvent);
                        }
                    }
                }
            }

            keyListener.getBuffer().forEach(InputEvent::consume);
            mouseListener.getBuffer().forEach(InputEvent::consume);
        }

        if (options.isDebug()) {
            drawDebug(g2d);
        }
        hovering = null;

        g2d.dispose();
        return bImg;
    }

    public void drawDebug(Graphics2D g2d) {
        Point mouse = new Point(0, 0);
        if (options.getMovingMouse() != null) {
            mouse = options.getMovingMouse().getPoint();
        }

        ArrayList<JTextDrawable> lines = new ArrayList<>();
        lines.add(new JTextDrawable("Version 0.1"));
        lines.add(new JTextDrawable(String.format("FPS: %.1f", options.getFps())));
        lines.add(new JTextDrawable(String.format("TICKS: %d", options.getTicks())));
        lines.add(new JTextDrawable("WIDTH: " + getWidth()));
        lines.add(new JTextDrawable("HEIGHT: " + getHeight()));
        lines.add(new JTextDrawable("MOUSE_X: " + mouse.getX()));
        lines.add(new JTextDrawable("MOUSE_Y: " + mouse.getY()));

        if (hovering != null) {
            lines.add(new JTextDrawable(hovering));
        }

        drawTextDrawables(g2d, lines);
    }

    public void drawTextDrawables(Graphics2D g2d, ArrayList<JTextDrawable> JTextDrawables) {
        g2d.setFont(DEFAULT_FONT);
        FontMetrics metric = g2d.getFontMetrics();
        int x = 10;
        int y = 10 + metric.getAscent() - metric.getDescent() - metric.getLeading();

        for (JTextDrawable line : JTextDrawables) {
            g2d.setColor(line.color);

            g2d.setFont(line.font);
            String[] text = line.text.toString().split("\n");
            if (text.length == 0) text = new String[]{""};
            for (int i = 0; i < text.length; i++) {
                if (i > 0) {
                    y += metric.getHeight();
                }

                String t = text[i];
                g2d.drawString(t, x, y);
            }

            if (line.newLine) {
                y += metric.getHeight();
                x = 10;
            } else {
                x += metric.stringWidth(text[text.length - 1]);
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(drawOnBuffer(), 0, 0, null);
    }

    public void update(Graphics g) {
        paint(g);
    }
}
