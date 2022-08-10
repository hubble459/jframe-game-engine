package jgameengine;

import java.awt.*;

import static jgameengine.JGameCanvas.DEFAULT_FONT;

public class JTextDrawable extends JDrawable {
    public Object text;
    public Color color;
    public boolean newLine;
    public Font font;

    public JTextDrawable(Object text) {
        this(text, true);
    }

    public JTextDrawable(Object text, boolean newLine) {
        super(null);
        this.text = text;
        this.newLine = newLine;
        this.font = DEFAULT_FONT;
        this.color = Color.GRAY;
    }
}
