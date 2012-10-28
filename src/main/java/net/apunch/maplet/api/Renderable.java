package net.apunch.maplet.api;

/**
 * Represents something that can be rendered to a map.
 */
public interface Renderable {
    /**
     * Renders this object to the given {@link Map}.
     * 
     * @param map
     *            Map that will be drawn upon
     */
    public void render(Map Map);
}
