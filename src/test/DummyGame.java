package test;

import jgameengine.JGameEngine;
import jgameengine.JGameOptions;

/**
 * Bouncing Ball
 */
public class DummyGame {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        JGameOptions.getInstance().setTicksASecond(120);
        JGameEngine gameEngine = new JGameEngine();

        // BALLS
        for (int i = 0; i < 100; i++) {
            CircleEntity ball = new CircleEntity(CircleEntity.randomSize());
            gameEngine.addDrawable(ball);
        }

        gameEngine.start();
    }
}
