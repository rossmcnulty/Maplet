package net.apunch.maplet.api.storage;

/**
 * Represents an abstract data key that can store various data.
 */
public abstract class DataKey {
    /**
     * Gets a boolean value from the given key.
     * 
     * @param key
     *            Key to search
     * @return Boolean value from the given key, or false if the value at the given key is not a boolean.
     */
    public abstract boolean getBoolean(String key);

    /**
     * Whether a key exists at the given key.
     * 
     * @param key
     *            Key to search
     * @return True if a key exists at the given key.
     */
    public abstract boolean keyExists(String key);

    /**
     * Assigns the given value to the given key.
     * 
     * @param key
     *            Key to assign value to
     * @param value
     *            Value to assign
     */
    public abstract void setBoolean(String key, boolean value);

    /**
     * Gets a boolean value from the given key.
     * 
     * @param key
     *            Key to search
     * @param def
     *            Default value to return if the given key does not exist
     * @return Boolean value from the given key, or the given default value if the key does not exist.
     */
    public boolean getBoolean(String key, boolean def) {
        if (keyExists(key)) {
            return getBoolean(key);
        }

        setBoolean(key, def);
        return def;
    }
}
