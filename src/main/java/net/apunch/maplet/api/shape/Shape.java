package net.apunch.maplet.api.shape;

import net.apunch.maplet.api.MapColor;
import net.apunch.maplet.api.attachment.Attachable;

/**
 * Represents a shape that can either be attached to a map directly or used to construct other {@link Attachable}s.
 */
public abstract class Shape extends Attachable {
    protected MapColor color = MapColor.DARK_GRAY;

    public Shape() {
    }

    public Shape(MapColor color) {
        this.color = color;
    }

    /**
     * Whether this shape intersects the given coordinates.
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @return True if this shape intersects the given coordinates
     */
    public abstract boolean intersects(int x, int y);

    /**
     * Sets the border color of this shape.
     * 
     * @param color
     *            Color to set
     */
    public void setBorderColor(MapColor color) {
        this.color = color;
    }
}
