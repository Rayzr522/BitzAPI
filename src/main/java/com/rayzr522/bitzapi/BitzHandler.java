package com.rayzr522.bitzapi;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.client.inv.InvUtils;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandCreateInv;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.utils.world.BitzTools;
import com.rayzr522.bitzapi.utils.world.LocUtils;
import com.rayzr522.bitzapi.world.PartialRegion;

public class BitzHandler implements Listener {

	private BitzAPI plugin;

	public BitzHandler(BitzAPI plugin) {

		this.plugin = plugin;

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		Action action = e.getAction();

		if (!action.equals(Action.LEFT_CLICK_BLOCK) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {

			return;

		}

		if (!BitzTools.isTool(e.getItem())) {

			return;

		}

		e.setCancelled(true);

		boolean left = action == Action.LEFT_CLICK_BLOCK;

		Player player = e.getPlayer();
		UUID id = player.getUniqueId();
		int toolType = BitzTools.getToolType(e.getItem());
		Location location = e.getClickedBlock().getLocation();

		switch (toolType) {

		case BitzTools.REGION_TOOL:

			PartialRegion partial = BitzData.getRegionSelection(id);

			if (left) {

				partial.setP1(location);
				plugin.messenger.playerInfo(player, "Region point 1 set");

			} else {

				partial.setP2(location);
				plugin.messenger.playerInfo(player, "Region point 2 set");

			}

			BitzData.setRegionSelection(id, partial);

			break;

		case BitzTools.LOCATION_TOOL:

			if (left) {

				BitzData.setLocationSelection(id, location);
				plugin.messenger.playerInfo(player, "Location selected");

			}

			break;

		case BitzTools.LOCATION_LIST_TOOL:

			List<Location> locations = BitzData.getLocationListSelection(id);
			String locationFormatted = LocUtils.toString(location);

			if (left) {

				if (!locations.contains(location)) {
					locations.add(location);
					plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " added");
				} else {
					plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " is already listed");
				}

			} else {

				if (locations.remove(location)) {

					plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " removed");

				} else {

					plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " isn't listed");

				}

			}

			BitzData.setLocationListSelection(id, locations);

			break;

		}

	}

	@EventHandler
	public void onCraftItem(CraftItemEvent e) {

		for (ItemStack item : e.getInventory().getContents()) {

			if (BitzTools.isTool(item)) {

				e.setCancelled(true);
				return;

			}

		}

	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {

		if (BitzTools.isTool(e.getSource())) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void onFurnaceFuel(FurnaceBurnEvent e) {

		if (BitzTools.isTool(e.getFuel())) {

			e.setCancelled(true);

		}

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		if (!InvUtils.isBitzInv(e.getInventory())) {
			return;
		}

		if (!BitzCommandCreateInv.isCreationInv(e.getInventory())) {
			e.setCancelled(true);
			// TODO: Fire off this event to all registered children
		}

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {

		if (!InvUtils.isBitzInv(e.getInventory())) {
			return;
		}

		if (BitzCommandCreateInv.isCreationInv(e.getInventory())) {

			Inventory inventory = BitzCommandCreateInv.removeCreationInv(e.getInventory());

			if (inventory != null) {

				ConfigurationSection base = plugin.configUtils.getSection("createdInvs");
				InvUtils.createConfigSection(inventory, base, plugin);
				plugin.configUtils.saveConfig();

			}

		}

	}

}
