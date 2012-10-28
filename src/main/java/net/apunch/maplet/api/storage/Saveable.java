package net.apunch.maplet.api.storage;

/**
 * Represents an object that can be saved to a {@link DataKey}.
 */
public interface Saveable {
    /**
     * Loads this object from the given {@link DataKey}.
     * 
     * @param key
     *            Key to load from
     */
    public void load(DataKey key);

    /**
     * Saves this object to the given {@link DataKey}.
     * 
     * @param key
     *            Key to save to
     */
    public void save(DataKey key);
}
