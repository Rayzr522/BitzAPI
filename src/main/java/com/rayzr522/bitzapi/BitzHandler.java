
package com.rayzr522.bitzapi;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class BitzHandler<T extends BitzPlugin> implements Listener {

    protected T plugin;

    public BitzHandler(T plugin) {

        this.plugin = plugin;

    }

    public void event(Event event) {

    }

}
