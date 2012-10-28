package net.apunch.maplet.api.event;

import net.apunch.maplet.api.Map;

import org.bukkit.event.HandlerList;

/**
 * Called when a {@link Map} is created.
 */
public class MapletCreateEvent extends MapletEvent {
    private static final HandlerList handlers = new HandlerList();

    public MapletCreateEvent(Map map) {
        super(map);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
