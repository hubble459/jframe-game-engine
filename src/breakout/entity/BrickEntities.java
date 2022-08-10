package breakout.entity;

import jgameengine.JGameOptions;
import jgameengine.JGameUtil;

import java.awt.*;

public class BrickEntities {
    private static final JGameOptions options = JGameOptions.getInstance();
    private static BrickEntity createBrick(int x, int y, Color color) {
        return new BrickEntity(new Rectangle(x, y, BrickEntity.WIDTH, BrickEntity.HEIGHT), color);
    }

    public static BrickEntity[] demoLevel(int line, int blocks) {
        int halfWidth = line * BrickEntity.WIDTH / 2;
        int startX = options.getWidth() / 2 - halfWidth;

        int x = startX;
        int y = 10;
        Color color = Color.CYAN;

        BrickEntity[] bricks = new BrickEntity[blocks];
        for (int i = 0; i < bricks.length; i++) {
            if (i % line == 0) {
                color = JGameUtil.randomColor();
                y += BrickEntity.HEIGHT;
                x = startX;
            }
            BrickEntity brick = createBrick(x, y, color);
            bricks[i] = brick;

            x += BrickEntity.WIDTH;
        }
        return bricks;
    }

    private final BrickEntity[] bricks;

    public BrickEntities() {
        bricks = demoLevel(20, 100);
    }

    public BrickEntity[] getBricks() {
        return bricks;
    }
}
