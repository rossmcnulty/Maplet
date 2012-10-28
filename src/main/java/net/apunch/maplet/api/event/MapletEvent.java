package net.apunch.maplet.api.event;

import net.apunch.maplet.api.Map;

import org.bukkit.event.Event;

/**
 * Represents an event called by Maplet.
 */
public abstract class MapletEvent extends Event {
    private final Map map;

    protected MapletEvent(Map map) {
        this.map = map;
    }

    /**
     * Gets the {@link Map} involved in this event.
     * 
     * @return Map involved in this event.
     */
    public Map getMap() {
        return map;
    }
}
