package net.apunch.maplet.api;

/**
 * Represents a color that can be displayed on a map.
 */
public enum MapColor {
    TRANSPARENT(0),
    FOREST_GREEN(4),
    SAND(8),
    GRAY(12),
    RED(16),
    LIGHT_BLUE(20),
    GREEN(28),
    WHITE(32),
    LIGHT_GRAY(36),
    BROWN(40),
    DARK_GRAY(44),
    BLUE(48),
    DARK_BROWN(52);

    private byte color;

    private MapColor(int color) {
        this.color = (byte) color;
    }

    /**
     * Gets the raw byte value of this map color.
     * 
     * @return Raw byte color.
     */
    public byte getRawColor() {
        return color;
    }

    /**
     * Gets a MapColor from the given raw byte value.
     * 
     * @param raw
     *            Raw color value
     * @return MapColor with the given byte value, or null if not found.
     */
    public static MapColor getColor(byte raw) {
        for (MapColor color : MapColor.values()) {
            if (color.color == raw) {
                return color;
            }
        }

        return null;
    }
}
