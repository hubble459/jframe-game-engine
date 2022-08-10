package jgameengine;

import jgameengine.listener.DefaultKeyListener;
import jgameengine.listener.DefaultMouseListener;

import java.awt.*;
import java.awt.event.MouseEvent;

public class JGameOptions {
    private static final JGameOptions instance = new JGameOptions();

    public static JGameOptions getInstance() {
        return instance;
    }

    private String title;
    private int width;
    private int height;
    private int ticksASecond;
    private int ticks;
    private float fps;
    private Color backgroundColor;
    private DefaultKeyListener keyListener;
    private DefaultMouseListener mouseListener;
    private MouseEvent movingMouse;
    private boolean fullscreen;
    private boolean debug;
    private boolean paused;

    private JGameOptions() {
        title = "JGameEngine";
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = dimension.width;
        height = dimension.height;
        ticksASecond = 30;
        keyListener = new DefaultKeyListener();
        mouseListener = new DefaultMouseListener();
        backgroundColor = Color.DARK_GRAY.darker();
        fullscreen = false;
        debug = false;
        paused = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTicksASecond() {
        return ticksASecond;
    }

    public void setTicksASecond(int ticksASecond) {
        this.ticksASecond = ticksASecond;
    }

    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = fps;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public DefaultKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(DefaultKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public DefaultMouseListener getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(DefaultMouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public MouseEvent getMovingMouse() {
        return movingMouse;
    }

    public void setMovingMouse(MouseEvent movingMouse) {
        this.movingMouse = movingMouse;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
