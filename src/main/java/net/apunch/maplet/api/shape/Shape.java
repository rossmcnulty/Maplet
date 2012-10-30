package net.apunch.maplet.api.shape;

import net.apunch.maplet.api.MapColor;
import net.apunch.maplet.api.attachment.Attachment;

/**
 * Represents a shape that can either be attached to a map directly or used to construct other {@link Attachment}s.
 */
public abstract class Shape extends Attachment {
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
        setDirty(true);
    }
}
