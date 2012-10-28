package net.apunch.maplet.api.shape;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

/**
 * Represents a circle that can be drawn to a map.
 */
public final class Circle extends Shape {
    private final int centerX;
    private final int centerY;
    private final int radius;

    /**
     * Constructs a circle with the given properties.
     * 
     * @param centerX
     *            X coordinate of the center point
     * @param centerY
     *            Y coordinate of the center point
     * @param radius
     *            Radius of the circle
     * @param color
     *            Color of the border of the circle
     */
    public Circle(int centerX, int centerY, int radius, MapColor color) {
        super(color);

        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public boolean intersects(int x, int y) {
        double distance = Math.pow((centerX - x), 2) + Math.pow((centerY - y), 2);
        return distance < Math.pow(radius, 2);
    }

    @Override
    public void render(Map map) {
        // TODO
    }
}
