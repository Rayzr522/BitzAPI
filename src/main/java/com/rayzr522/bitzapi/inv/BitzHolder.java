
package com.rayzr522.bitzapi.inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.rayzr522.bitzapi.BitzPlugin;

public class BitzHolder implements InventoryHolder {

	private BitzPlugin	plugin;
	private Inventory	inv;

	public BitzHolder(BitzPlugin plugin, Inventory inv) {

		this.plugin = plugin;
		this.inv = inv;

	}

	public Inventory getInventory() {
		return inv;
	}
	
	public BitzPlugin getPlugin() {
		return plugin;
	}

}
