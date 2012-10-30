package net.apunch.maplet.api.attachment;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

public abstract class Button extends SelectableAttachment {
    private final Label label;

    protected Button(int x, int y, String text) {
        super(x, y);
        label = new Label(text, x, y, MapColor.GRAY);
        addAttachment(label);
    }

    @Override
    public void onDeselect(Map map) {
        label.setColor(MapColor.GRAY);
    }

    @Override
    public void onSelect(Map map) {
        label.setColor(MapColor.GREEN);
    }
}
