package net.apunch.maplet.api.attachment;

import net.apunch.maplet.api.Map;

/**
 * Represents an attachment that can be selected. When selected, this attachment will be bordered by a yellow outline on
 * the map.
 */
public abstract class SelectableAttachment extends Attachment {
    protected int x;
    protected int y;
    protected boolean selected;

    protected SelectableAttachment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Called when this attachment is clicked on the given {@link Map} (when the user presses the SHIFT key).
     * 
     * @param map
     *            Map that contains this attachment
     */
    public void onClick(Map map) {
    }

    /**
     * Called when this attachment is deselected on the given {@link Map}.
     * 
     * @param map
     *            Map that contains this attachment
     */
    public void onDeselect(Map map) {
    }

    /**
     * Called when this attachment is selected on the given {@link Map}.
     * 
     * @param map
     *            Map that contains this attachment
     */
    public void onSelect(Map map) {
    }

    /**
     * Sets this attachment as selected.
     * 
     * @param selected
     *            Whether this attachment should be selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
