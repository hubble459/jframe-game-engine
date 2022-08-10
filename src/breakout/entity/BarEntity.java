package breakout.entity;

import jgameengine.JDrawable;
import jgameengine.JGameOptions;
import jgameengine.JGameUtil;

import java.awt.*;

public class BarEntity extends JDrawable {
    private static final JGameOptions options = JGameOptions.getInstance();
    private static final int WIDTH = 50;
    private static final int HEIGHT = 20;

    public BarEntity(int x) {
        super(new Rectangle(x, options.getHeight() - 100, WIDTH, HEIGHT));
        setBordered(false);
        setFillColor(Color.WHITE);
        followMouse();
    }

    public void followMouse() {
        JGameUtil.mouse(mouseEvent -> translateX(mouseEvent.getX() - getX()));
    }

    @Override
    public boolean tick() {

        return super.tick();
    }
}
