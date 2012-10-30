package net.apunch.maplet.api.attachment;

import org.bukkit.map.MapFont;
import org.bukkit.map.MinecraftFont;
import org.bukkit.map.MapFont.CharacterSprite;

import net.apunch.maplet.api.Map;
import net.apunch.maplet.api.MapColor;

/**
 * Represents a string of text that can be attached to a {@link Map}.
 */
public class Label extends Attachment {
    private static final MapFont MINECRAFT_FONT = new MinecraftFont();

    private final String text;
    private final int x;
    private final int y;
    private MapColor color;

    /**
     * Constructs a label with text in one {@link MapColor}.
     * 
     * @param text
     *            Text to display
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param color
     *            Color to use
     */
    public Label(String text, int x, int y, MapColor color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Constructs a label with text, the text may contain color codes.
     * 
     * @param text
     *            Text to display
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     */
    public Label(String text, int x, int y) {
        this(text, x, y, null);
    }

    @Override
    public void draw(Map map) {
        int xx = x;
        int yy = y;
        int startX = xx;
        if (!MINECRAFT_FONT.isValid(text)) {
            throw new IllegalArgumentException("text contains invalid characters");
        }

        MapColor set = color;
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                xx = startX;
                yy += MINECRAFT_FONT.getHeight() + 1;
                continue;
            } else if (ch == '\u00A7' && color == null) {
                int j = text.indexOf(';', i);
                if (j >= 0) {
                    try {
                        set = MapColor.getColor(Byte.parseByte(text.substring(i + 1, j)));
                        i = j;
                        continue;
                    } catch (NumberFormatException ex) {
                    }
                }
            }

            CharacterSprite sprite = MINECRAFT_FONT.getChar(text.charAt(i));
            for (int r = 0; r < MINECRAFT_FONT.getHeight(); ++r) {
                for (int c = 0; c < sprite.getWidth(); ++c) {
                    if (sprite.get(r, c)) {
                        map.setPixel(xx + c, yy + r, set);
                    }
                }
            }
            xx += sprite.getWidth() + 1;
        }
    }

    /**
     * Sets the color of this label's text.
     * 
     * @param color
     *            Color to use
     */
    public void setColor(MapColor color) {
        this.color = color;
        setDirty(true);
    }
}
