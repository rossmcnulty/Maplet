package net.apunch.maplet.api;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.map.MapPalette;

import net.apunch.maplet.api.menu.MainMenu;
import net.apunch.maplet.api.menu.Menu;
import net.minecraft.server.Packet131ItemData;

/**
 * Represents a map that can be drawn to.
 */
public final class Map {
    private final byte[] pixels = new byte[128 * 128];
    // private final byte[][] data = new byte[128][131];
    private final Set<Player> viewers = new HashSet<Player>();
    private final short id;
    private Menu activeMenu;

    public Map(short id) {
        this.id = id;
        setActiveMenu(new MainMenu());
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
        if (!viewers.isEmpty()) {
            activeMenu.draw(this);

            // Create packets outside of player loop
            List<Packet131ItemData> changed = new ArrayList<Packet131ItemData>();
            for (int column = 0; column < 128; column++) {
                byte[] data = new byte[131];
                data[1] = (byte) column;
                int index = 3;
                for (int i = column; i < pixels.length; i += 128) {
                    data[index++] = pixels[i];
                }

                changed.add(new Packet131ItemData((short) 358, id, data));
            }

            // Send all changed columns to viewers
            for (Player player : viewers) {
                for (Packet131ItemData packet : changed) {
                    ((CraftPlayer) player).getHandle().netServerHandler.sendPacket(packet);
                }
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

        // Clear old pixels from map TODO: Clear pixels per-attachment using some removeAttachment method
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                setPixel(x, y, MapColor.TRANSPARENT);
            }
        }

        activeMenu = menu;
        activeMenu.draw(this);
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
        pixels[x + y * 128] = color;
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
