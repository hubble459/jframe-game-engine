package breakout;

import breakout.entity.BallEntity;
import breakout.entity.BarEntity;
import breakout.entity.BrickEntities;
import breakout.entity.BrickEntity;
import jgameengine.JGameEngine;
import jgameengine.JGameOptions;

public class Breakout {
    public static void main(String[] args) {
        JGameOptions options = JGameOptions.getInstance();
        options.setTitle("Breakout!");
        options.setFullscreen(true);
        options.setTicksASecond(60);

        JGameEngine breakout = new JGameEngine();

        BrickEntities bricks = new BrickEntities();
        for (BrickEntity brick : bricks.getBricks()) {
            breakout.addDrawable(brick);
        }

        BallEntity ball = new BallEntity(options.getWidth() / 2, options.getHeight() - 100, 20);
        breakout.addDrawable(ball);

        BarEntity bar = new BarEntity(options.getWidth() / 2);
        breakout.addDrawable(bar);

        breakout.start();
    }
}
