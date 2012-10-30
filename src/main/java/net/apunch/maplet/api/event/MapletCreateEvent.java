package net.apunch.maplet.api.event;

import net.apunch.maplet.api.Map;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when a map item is registered as a {@link Map}.
 */
public class MapletCreateEvent extends MapletEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    public MapletCreateEvent(Map map) {
        super(map);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
