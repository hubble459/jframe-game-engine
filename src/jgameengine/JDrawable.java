package jgameengine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class JDrawable {
    private Shape shape;
    private boolean filled = true;
    private boolean bordered = true;
    private Color fillColor = Color.BLACK;
    private Color borderColor = Color.BLACK;
    private int borderSize = 2;
    private boolean changed = true;

    public JDrawable(Shape shape) {
        this.shape = shape;
    }

    public JDrawable(Shape shape, int x, int y) {
        this.shape = shape;
        this.shape.getBounds().x = x;
        this.shape.getBounds().y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(borderSize));
        if (filled) {
            g2d.setColor(fillColor);
            g2d.fill(shape);
        }
        if (bordered) {
            g2d.setColor(borderColor);
            g2d.draw(shape);
        }
    }

    public void onKeyPress(KeyEvent e) {
    }

    public void onClick(MouseEvent e) {
    }

    public void onHover(MouseEvent e) {
    }

    public void remove() {
        JGameUtil.remove(this);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        changed = true;
        this.shape = shape;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        changed = true;
        this.filled = filled;
    }

    public boolean isBordered() {
        return bordered;
    }

    public void setBordered(boolean bordered) {
        changed = true;
        this.bordered = bordered;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        changed = true;
        this.fillColor = fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        changed = true;
        this.borderColor = borderColor;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        changed = true;
        this.borderSize = borderSize;
    }

    public void translateX(int tx) {
        translate(tx, 0);
    }

    public int getX() {
        return (int) this.shape.getBounds().getX();
    }

    public void translateY(int ty) {
        translate(0, ty);
    }

    public int getY() {
        return (int) this.shape.getBounds().getY();
    }

    private void translate(int tx, int ty) {
        changed = true;
        AffineTransform at = new AffineTransform();
        at.translate(tx, ty);
        this.shape = at.createTransformedShape(shape);
    }

    public void setWidth(int width) {
        changed = true;
        this.shape.getBounds().width = width;
    }

    public int getWidth() {
        return (int) this.shape.getBounds().getWidth();
    }

    public void setHeight(int height) {
        changed = true;
        this.shape.getBounds().height = height;
    }

    public int getHeight() {
        return (int) this.shape.getBounds().getHeight();
    }

    public int getTop() {
        return getY();
    }

    public int getLeft() {
        return getX();
    }

    public int getBottom() {
        return getTop() + getHeight();
    }

    public int getRight() {
        return getLeft() + getWidth();
    }

    @Override
    public String toString() {
        return super.toString() + " {" +
                "\n  X: " + getX() +
                "\n  Y: " + getY() +
                "\n  shape=" + shape +
                "\n  filled=" + filled +
                "\n  bordered=" + bordered +
                "\n  fillColor=" + fillColor +
                "\n  borderColor=" + borderColor +
                "\n  borderSize=" + borderSize +
                "\n}";
    }

    public boolean tick() {
        if (changed) {
            changed = false;
            return true;
        } else {
            return false;
        }
    }
}
