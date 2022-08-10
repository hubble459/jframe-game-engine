import jgameengine.JGameEngine;
import jgameengine.JGameOptions;

public class Main {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        JGameOptions options = JGameOptions.getInstance();
        options.setTitle("Testing");
        options.setDebug(true);

        JGameEngine game = new JGameEngine();
        game.start();
    }
}
