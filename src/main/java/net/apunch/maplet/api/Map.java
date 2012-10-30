package net.apunch.maplet.api;

import java.awt.Image;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MinecraftFont;
import org.bukkit.map.MapFont.CharacterSprite;

import net.apunch.maplet.api.menu.MainMenu;
import net.apunch.maplet.api.menu.Menu;
import net.minecraft.server.Packet131ItemData;

/**
 * Represents a map that can be drawn to.
 */
public final class Map {
    private static final MapFont MINECRAFT_FONT = new MinecraftFont();

    private final byte[][] data = new byte[128][131];
    private final Set<Player> viewers = new HashSet<Player>();
    private final short id;
    private Menu activeMenu;

    public Map(short id) {
        this.id = id;

        for (int x = 0; x < 128; x++) {
            data[x][1] = (byte) x;
        }
    }

    /**
     * Adds the given player to this map.
     * 
     * @param player
     *            Player to add as a viewer
     * @return True if the player was successfully added.
     */
    public boolean addViewer(Player player) {
        return viewers.add(player);
    }

    public void cycleSelected(Action action) {
        // TODO: Handle this elsewhere?
        if (activeMenu != null) {
            int direction = 0;
            switch (action) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                direction = -1;
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                direction = 1;
                break;
            default:
                return;
            }

            activeMenu.cycleSelected(this, direction);
        }
    }

    /**
     * Draws the specified image at the given coordinates.
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param image
     *            Image to draw
     */
    public void drawImage(int x, int y, Image image) {
        byte[] bytes = MapPalette.imageToBytes(image);
        int width = image.getWidth(null);
        for (int xx = 0; xx < image.getWidth(null); ++xx) {
            for (int yy = 0; yy < image.getHeight(null); ++yy) {
                setPixel(x + xx, y + yy, bytes[yy * width + xx]);
            }
        }
    }

    /**
     * Draws a line using the specified parameters.
     * 
     * @param startX
     *            X coordinate to start line at
     * @param startY
     *            Y coordinate to start line at
     * @param endX
     *            X coordinate to end line at
     * @param endY
     *            Y coordinate to end line at
     * @param color
     *            Color to draw the line in
     */
    public void drawLine(int startX, int startY, int endX, int endY, MapColor color) {
        int lengthX = Math.abs(startX - endX);
        int lengthY = Math.abs(startY - endY);
        int motionX = 1;
        int motionY = 1;
        int error = lengthX - lengthY;
        int e2;
        if (startX > endX) {
            motionX = -1;
        }
        if (startY > endY) {
            motionY = -1;
        }

        while (true) {
            setPixel(startX, startY, color);
            if (startX == endX && startY == endY) {
                break;
            }

            e2 = error * 2;
            if (e2 > -lengthY) {
                error -= lengthY;
                startX += motionX;
            }
            if (e2 < lengthX) {
                error += lengthX;
                startY += motionY;
            }
        }
    }

    /**
     * Draws the given text at the specified coordinates in the given {@link MapColor}.
     * 
     * @param text
     *            Text to draw
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param color
     *            Color to draw text in
     */
    public void drawText(String text, int x, int y, MapColor color) {
        int startX = x;
        if (!MINECRAFT_FONT.isValid(text)) {
            throw new IllegalArgumentException("text contains invalid characters");
        }

        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                x = startX;
                y += MINECRAFT_FONT.getHeight() + 1;
                continue;
            } else if (ch == '\u00A7') {
                int j = text.indexOf(';', i);
                if (j >= 0) {
                    try {
                        color = MapColor.getColor(Byte.parseByte(text.substring(i + 1, j)));
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
                        setPixel(x + c, y + r, color);
                    }
                }
            }
            x += sprite.getWidth() + 1;
        }
    }

    /**
     * Gets this map's active menu.
     * 
     * @return Map's active menu, or null if there is not an active menu.
     */
    public Menu getActiveMenu() {
        return activeMenu;
    }

    /**
     * Whether the given player is viewing this map.
     * 
     * @param player
     *            Player to check
     * @return True if the given player is viewing this map.
     */
    public boolean isViewer(Player player) {
        return viewers.contains(player);
    }

    /**
     * Removes the given player from this map.
     * 
     * @param player
     *            Player to remove
     * @return True if the player was successfully removed.
     */
    public boolean removeViewer(Player player) {
        return viewers.remove(player);
    }

    public void render() {
        if (activeMenu == null) {
            setActiveMenu(new MainMenu());
        }
        activeMenu.render(this);

        for (Player player : viewers) {
            for (int x = 0; x < 128; x++) {
                ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(new Packet131ItemData((short) 358, id, data[x]));
            }
        }
    }

    /**
     * Sets the active menu for this map.
     * 
     * @param menu
     *            Menu to set as active
     */
    public void setActiveMenu(Menu menu) {
        // Set the new menu's parent if the active menu is available
        if (activeMenu != null) {
            menu.setParent(activeMenu);
        }
        activeMenu = menu;

        // Clear old pixels from map
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                setPixel(x, y, MapColor.TRANSPARENT);
            }
        }
    }

    /**
     * Sets the pixel at the given coordinates to the given byte color value.
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param color
     *            Color to set
     */
    public void setPixel(int x, int y, byte color) {
        data[x][y + 3] = color;
    }

    /**
     * Sets the pixel at the given coordinates to the given {@link MapColor}.
     * 
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param color
     *            Color to set
     */
    public void setPixel(int x, int y, MapColor color) {
        setPixel(x, y, color.getRawColor());
    }
}
