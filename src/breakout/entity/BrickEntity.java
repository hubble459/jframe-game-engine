package breakout.entity;

import jgameengine.JDrawable;

import java.awt.*;

public class BrickEntity extends JDrawable {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 25;
    public static final Color BORDER = Color.PINK;

    public BrickEntity(Shape shape, Color color) {
        super(shape);
        setBorderColor(BORDER);
        setFillColor(color);
    }
}
