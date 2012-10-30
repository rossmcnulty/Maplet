package net.apunch.maplet.api.shape;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

/**
 * Represents a rectangle that can be drawn to a map.
 */
public final class Rectangle extends Shape {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Constructs a rectangle with the given properties.
     * 
     * @param x
     *            X coordinate of the rectangle
     * @param y
     *            Y coordinate of the rectangle
     * @param width
     *            Width of the rectangle
     * @param height
     *            Height of the rectangle
     * @param color
     *            Color of the border of the rectangle
     */
    public Rectangle(int x, int y, int width, int height, MapColor color) {
        super(color);

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Map map) {
        map.drawLine(x, y, x, y + height, color);
        map.drawLine(x, y + height, x + width, y + height, color);
        map.drawLine(x + width, y + height, x + width, y, color);
        map.drawLine(x + width, y, x, y, color);
    }

    @Override
    public boolean intersects(int x, int y) {
        return false;
    }
}
