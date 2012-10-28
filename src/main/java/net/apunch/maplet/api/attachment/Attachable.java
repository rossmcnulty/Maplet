package net.apunch.maplet.api.attachment;

import java.util.ArrayList;
import java.util.List;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.Renderable;

/**
 * Represents something that can be attached to a {@link Map}. An attachment contains its own list of Attachables.
 */
public abstract class Attachable implements Renderable {
    protected final List<SelectableAttachment> selectables = new ArrayList<SelectableAttachment>();
    private final List<Attachable> attachments = new ArrayList<Attachable>();

    @Override
    public void render(Map map) {
        for (Attachable attachment : attachments) {
            attachment.render(map);
        }
    }

    /**
     * Adds an attachment to this attachment.
     * 
     * @param attachment
     *            Attachment to add
     */
    protected void addAttachment(Attachable attachment) {
        attachments.add(attachment);
        if (attachment instanceof SelectableAttachment) {
            selectables.add((SelectableAttachment) attachment);
        }
    }
}
