package net.apunch.maplet.api.attachment;

import java.awt.Image;

import net.apunch.maplet.Maplet;
import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;
import net.apunch.maplet.api.shape.Rectangle;

/**
 * Represents a rectangular application icon with an image in the center.
 */
public final class ApplicationIcon extends SelectableAttachment {
    private final Rectangle rectangle;
    private String name;
    private Image image;

    /**
     * Constructs an application icon using the given {@link Rectangle} and {@link Image}.
     * 
     * @param name
     *            Name of this icon
     * @param x
     *            X coordinate of the border rectangle
     * @param y
     *            Y coordinate of the border rectangle
     * @param width
     *            Width of the border rectangle
     * @param height
     *            Height of the border rectangle
     * @param color
     *            Color of the border rectangle
     * @param image
     *            Image to render within the rectangle
     */
    public ApplicationIcon(String name, int x, int y, int width, int height, MapColor color, Image image) {
        super(x, y);

        this.name = name;
        if (image == null) {
            image = Maplet.getImage("missing.png");
        }
        this.image = image;

        rectangle = new Rectangle(x, y, width, height, color);
        addAttachment(rectangle);
    }

    @Override
    public void onClick(Map map) {
        Maplet.getApplication(name).onOpen(map);
    }

    @Override
    public void onDeselect(Map map) {
        rectangle.setBorderColor(MapColor.WHITE);
    }

    @Override
    public void onSelect(Map map) {
        rectangle.setBorderColor(MapColor.FOREST_GREEN);
    }

    @Override
    public void render(Map map) {
        map.drawImage(x, y, image);

        super.render(map); // Render rectangle border after image icon
    }

    /**
     * Gets the name of this icon.
     * 
     * @return Name of this icon.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the image to render within this icon.
     * 
     * @param image
     *            Image to render
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Sets the name of this icon.
     * 
     * @param name
     *            Name to give this icon
     */
    public void setName(String name) {
        this.name = name;
    }
}
