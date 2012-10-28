package net.apunch.maplet.api.attachment;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

public abstract class Button extends SelectableAttachment {
    private final String text;

    protected Button(int x, int y, String text) {
        super(x, y);
        this.text = text;
    }

    @Override
    public void render(Map map) {
        map.drawText(text, x, y, selected ? MapColor.FOREST_GREEN : MapColor.DARK_GRAY);
    }
}
