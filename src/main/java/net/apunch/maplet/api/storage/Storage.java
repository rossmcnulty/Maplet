package net.apunch.maplet.api.storage;

/**
 * Represents something that can be saved to and loaded from.
 */
public interface Storage {
    /**
     * Gets the {@link DataKey} from the given key.
     * 
     * @param key
     *            Key to search
     * @return DataKey from the given key, or null if no key was found.
     */
    public DataKey getKey(String key);

    /**
     * Loads this storage object.
     */
    public void load();

    /**
     * Saves this storage object.
     */
    public void save();
}
