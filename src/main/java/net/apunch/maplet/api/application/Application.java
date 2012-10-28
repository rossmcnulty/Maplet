package net.apunch.maplet.api.application;

import java.awt.Image;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.storage.Saveable;

/**
 * Represents an application that can perform a variety of functions on a map.
 */
public interface Application extends Saveable {
    /**
     * Gets the {@link Image} to display as this application's icon on the main menu of a map.
     * 
     * @return Image to use as this application's icon.
     */
    public Image getIcon();

    /**
     * Gets the name of this application.
     * 
     * @return Name of this application.
     */
    public String getName();

    /**
     * Called when this application is opened on the given {@link Map}.
     * 
     * @param map
     *            Map that this application is being opened on
     */
    public void onOpen(Map map);
}
