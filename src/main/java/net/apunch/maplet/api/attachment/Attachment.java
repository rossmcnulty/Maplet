package net.apunch.maplet.api.attachment;

import java.util.ArrayList;
import java.util.List;

import net.apunch.maplet.Maplet;
import net.apunch.maplet.api.Map;

/**
 * Represents something that can be attached to a {@link Map}.
 */
public abstract class Attachment {
    protected final List<SelectableAttachment> selectables = new ArrayList<SelectableAttachment>();
    private final List<Attachment> attachments = new ArrayList<Attachment>();
    private boolean dirty = true;

    /**
     * Called upon being attached to the given {@link Map}.
     * 
     * @param map
     *            Map this attachment will be drawn to
     */
    public void draw(Map map) {
        for (Attachment attachment : attachments) {
            if (attachment.isDirty()) {
                Maplet.log("Rendering attachment: " + attachment.toString());
                attachment.draw(map);
                attachment.setDirty(false);
            }
        }
    }

    /**
     * Gets whether this attachment has been modified since the last render pass.
     * 
     * @return True if this attachment has been modified.
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * Sets whether this attachment has been modified since the last render pass.
     * 
     * @param dirty
     *            Whether this attachment has been modified
     */
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    /**
     * Adds an attachment to this attachment.
     * 
     * @param attachment
     *            Attachment to add
     */
    protected final void addAttachment(Attachment attachment) {
        attachments.add(attachment);
        if (attachment instanceof SelectableAttachment) {
            selectables.add((SelectableAttachment) attachment);
        }

        dirty = true;
    }
}
