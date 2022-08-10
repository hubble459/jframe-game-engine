package test;

import jgameengine.JDrawable;
import jgameengine.JGameOptions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class CircleEntity extends JDrawable {
    private static final JGameOptions options = JGameOptions.getInstance();
    private static final Random random = new Random();
    private int xSpeed, ySpeed;
    private boolean hover;

    public CircleEntity(int size) {
        this(new Ellipse2D.Double(randomX(), randomY(), size, size));
        xSpeed = random.nextInt(8) + 2;
        ySpeed = random.nextInt(8) + 2;

        double relativeToTicks = 30. / options.getTicksASecond();
        xSpeed = (int) Math.max(1, xSpeed * relativeToTicks);
        ySpeed = (int) Math.max(1, ySpeed * relativeToTicks);

        if (random.nextBoolean()) {
            if (random.nextBoolean()) {
                xSpeed = -xSpeed;
            } else {
                ySpeed = -ySpeed;
            }
        }
    }

    private static int randomX() {
        return random.nextInt(options.getWidth() - 100);
    }

    private static int randomY() {
        return random.nextInt(options.getHeight() - 100);
    }

    public static int randomSize() {
        return random.nextInt(75 - 25) + 25;
    }

    private CircleEntity(Shape shape) {
        super(shape);
    }

    @Override
    public boolean tick() {
        int x = getX();
        int y = getY();

        if (x <= 0) {
            xSpeed = reverse(xSpeed);
        } else if (x >= options.getWidth()) {
            xSpeed = reverse(xSpeed);
        }

        if (y <= 0) {
            ySpeed = reverse(ySpeed);
        } else if (y >= options.getHeight()) {
            ySpeed = reverse(ySpeed);
        }

        translateX(xSpeed);
        translateY(ySpeed);

        return super.tick();
    }

    private int reverse(int i) {
        if (i > 0) {
            return -i;
        } else {
            return Math.abs(i);
        }
    }

    @Override
    public void onHover(MouseEvent e) {
        hover = !hover;
        if (hover) {
            setBorderColor(randomColor());
        } else {
            setFillColor(randomColor());
        }
    }

    @Override
    public void onClick(MouseEvent e) {
        setFilled(!isFilled());
    }

    private Color randomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
