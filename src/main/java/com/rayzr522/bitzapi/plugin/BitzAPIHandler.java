
package com.rayzr522.bitzapi.plugin;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.BitzHandler;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandCreateInv;
import com.rayzr522.bitzapi.inv.MenuManager;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.utils.world.BitzTools;
import com.rayzr522.bitzapi.utils.world.BitzTools.ToolType;
import com.rayzr522.bitzapi.utils.world.LocUtils;
import com.rayzr522.bitzapi.world.PartialRegion;

public class BitzAPIHandler extends BitzHandler<BitzAPI> {

	public BitzAPIHandler(BitzAPI plugin) {
		super(plugin);
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
		boolean left = action == Action.LEFT_CLICK_BLOCK;

		Player player = e.getPlayer();
		UUID id = player.getUniqueId();
		ToolType toolType = BitzTools.getToolType(e.getItem());
		Location location = e.getClickedBlock().getLocation();

		if (toolType == null) { return; }

		e.setCancelled(true);

		if (toolType.getPluginName().equals(BitzAPI.instance.getName())) {

			if (toolType.getName().equals(ToolType.REGION_TOOL_NAME)) {

				PartialRegion partial = BitzData.getRegionSelection(id);

				if (left) {

					partial.setP1(location);
					plugin.messenger.playerInfo(player, "Region point 1 set");

				} else {

					partial.setP2(location);
					plugin.messenger.playerInfo(player, "Region point 2 set");

				}

				BitzData.setRegionSelection(id, partial);

			}

			else if (toolType.getName().equals(ToolType.LOCATION_TOOL_NAME)) {

				if (left) {

					BitzData.setLocationSelection(id, location);
					plugin.messenger.playerInfo(player, "Location selected");

				}

			}

			else if (toolType.getName().equals(ToolType.LOCATION_LIST_TOOL)) {

				List<Location> locations = BitzData.getLocationListSelection(id);
				String locationFormatted = LocUtils.toString(location);

				if (left) {

					if (!locations.contains(location)) {
						locations.add(location);
						plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " added");
					} else {
						plugin.messenger.playerWarning(player, "Location at " + locationFormatted + " is already listed");
					}

				} else {

					if (locations.remove(location)) {

						plugin.messenger.playerInfo(player, "Location at " + locationFormatted + " removed");

					} else {

						plugin.messenger.playerWarning(player, "Location at " + locationFormatted + " isn't listed");

					}

				}

				BitzData.setLocationListSelection(id, locations);

			}

		} else {
			// TODO: Send the event to a list of registered tools-handlers
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

		if (!MenuManager.isMenu(e.getInventory())) { return; }

		if (!BitzCommandCreateInv.isCreationInv(e.getInventory())) {
			e.setCancelled(true);
			// TODO: Fire off this event to all registered children
		}

	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {

		if (!MenuManager.isMenu(e.getInventory())) { return; }

		if (BitzCommandCreateInv.isCreationInv(e.getInventory())) {

			Inventory inventory = BitzCommandCreateInv.removeCreationInv(e.getInventory());

			if (inventory != null) {

				ConfigurationSection base = plugin.configUtils.getSection("createdInvs");
				MenuManager.createConfigSection(inventory, base, plugin);
				plugin.configUtils.saveConfig();

			}

		}

	}

}
