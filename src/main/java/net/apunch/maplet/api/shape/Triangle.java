package net.apunch.maplet.api.shape;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

/**
 * Represents a triangle that can be drawn to a map.
 */
public final class Triangle extends Shape {
    private final int firstX;
    private final int firstY;
    private final int secondX;
    private final int secondY;
    private final int thirdX;
    private final int thirdY;

    /**
     * Constructs a triangle with the given properties.
     * 
     * @param firstX
     *            X coordinate of first point
     * @param firstY
     *            Y coordinate of first point
     * @param secondX
     *            X coordinate of second point
     * @param secondY
     *            Y coordinate of second point
     * @param thirdX
     *            X coordinate of third point
     * @param thirdY
     *            Y coordinate of third point
     * @param color
     *            Color of the border of the triangle
     */
    public Triangle(int firstX, int firstY, int secondX, int secondY, int thirdX, int thirdY, MapColor color) {
        super(color);

        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
        this.thirdX = thirdX;
        this.thirdY = thirdY;
    }

    @Override
    public void draw(Map map) {
        map.drawLine(firstX, firstY, secondX, secondY, color);
        map.drawLine(secondX, secondY, thirdX, thirdY, color);
        map.drawLine(thirdX, thirdY, firstX, firstY, color);
    }

    @Override
    public boolean intersects(int x, int y) {
        return false;
    }
}
