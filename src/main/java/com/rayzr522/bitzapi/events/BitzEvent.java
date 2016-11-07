
package com.rayzr522.bitzapi.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class BitzEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
