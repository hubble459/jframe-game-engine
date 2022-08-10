package breakout.entity;

import jgameengine.JDrawable;
import jgameengine.JGameOptions;
import jgameengine.JGameUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class BallEntity extends JDrawable {
    private static final JGameOptions options = JGameOptions.getInstance();
    private static final Random random = new Random();
    private int xSpeed, ySpeed;
    private final int startSpeedX;
    private final int startSpeedY;
    private final Shape startShape;

    public BallEntity(int x, int y, int size) {
        super(new Ellipse2D.Double(x, y, size, size));
        setBordered(false);
        xSpeed = random.nextInt(14) - 7;
        ySpeed = 9;

        double relativeToTicks = 30. / options.getTicksASecond();
        xSpeed = (int) Math.max(1, xSpeed * relativeToTicks);
        ySpeed = (int) Math.max(1, ySpeed * relativeToTicks);

        ySpeed = -ySpeed;

        startSpeedX = xSpeed;
        startSpeedY = ySpeed;
        startShape = getShape();
    }


    @Override
    public boolean tick() {
        int x = getX();
        int y = getY();

        hitBrick();

        if (x <= 0) {
            xSpeed = reverse(xSpeed);
        } else if (x + getWidth() >= options.getWidth()) {
            xSpeed = reverse(xSpeed);
        }

        if (ySpeed > 0 && hitBar()) {
            ySpeed = startSpeedY;
        }

        if (y <= 0) {
            ySpeed = -startSpeedY;
        }
        if (y + getHeight() * 1.5 >= options.getHeight()) {
            ySpeed = reverse(ySpeed);
            gameOver();
        } else {
            translateX(xSpeed);
            translateY(ySpeed);
        }

        return true;
    }

    private void hitBrick() {
        ArrayList<BrickEntity> bricks = JGameUtil.getDrawables(BrickEntity.class);
        for (BrickEntity brick : bricks) {
            if (getShape().intersects(brick.getShape().getBounds())) {

                Rectangle R1 = getShape().getBounds();
                Rectangle R2 = brick.getShape().getBounds();

                int m = getWidth(); // margin

                if (R1.getY() + m > R2.getY() + R2.getHeight()) {
                    ySpeed = -ySpeed;
                } else if (R1.getY() + R1.getHeight() - m < R2.getY()) {
                    ySpeed = Math.abs(ySpeed);
                } else if (R1.getX() + m > R2.getX() + R2.getWidth()) {
                    xSpeed = -xSpeed;
                } else if (R1.getX() + R1.getWidth() - m < R2.getX()) {
                    xSpeed = Math.abs(xSpeed);
                }


                translateY(ySpeed);
                translateY(xSpeed);

                brick.remove();

            }
        }
    }

    private boolean hitBar() {
        if (getY() >= options.getHeight() - 100) {
            ArrayList<BarEntity> bars = JGameUtil.getDrawables(BarEntity.class);
            for (BarEntity bar : bars) {
                Shape barShape = bar.getShape();
                if (barShape.intersects(getShape().getBounds())) {
                    double cX = getShape().getBounds().getCenterX();
                    double cXb = barShape.getBounds().getCenterX();
                    xSpeed += cX - cXb;
                    ySpeed += cX - cXb;

                    xSpeed = Math.min(10, xSpeed);
                    ySpeed = Math.min(10, ySpeed);

                    xSpeed = Math.max(-10, xSpeed);
                    ySpeed = Math.max(-10, ySpeed);

                    return true;
                }
            }
        }

        return false;
    }

    private void gameOver() {
        JGameUtil.write("GAME OVER!");
        JGameUtil.write("PRESS ANY KEY TO PLAY AGAIN");
        JGameUtil.waitFor(JGameUtil.ANY_KEY, (KeyEvent e) -> {
            if (e != null) {
                xSpeed = startSpeedX;
                ySpeed = startSpeedY;
                setShape(startShape);
                JGameUtil.undo(2);
                options.setPaused(false);
            }
        });
    }

    private int reverse(int i) {
        if (i > 0) {
            return -i;
        } else {
            return Math.abs(i);
        }
    }
}
