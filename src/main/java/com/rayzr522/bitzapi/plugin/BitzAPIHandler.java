
package com.rayzr522.bitzapi.plugin;

import static mirror.Mirror.$;

import java.lang.reflect.Array;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.comphenix.tinyprotocol.Reflection;
import com.comphenix.tinyprotocol.Reflection.FieldAccessor;
import com.comphenix.tinyprotocol.Reflection.MethodInvoker;
import com.comphenix.tinyprotocol.TinyProtocol;
import com.rayzr522.bitzapi.BitzHandler;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandCreateInv;
import com.rayzr522.bitzapi.inv.MenuManager;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.utils.data.ListUtils;
import com.rayzr522.bitzapi.utils.data.LoreData;
import com.rayzr522.bitzapi.utils.world.BitzTools;
import com.rayzr522.bitzapi.utils.world.BitzTools.ToolType;
import com.rayzr522.bitzapi.utils.world.LocUtils;
import com.rayzr522.bitzapi.world.PartialRegion;

import io.netty.channel.Channel;
import mirror.PacketBuilder;
import mirror.PlayerWrapper;

public class BitzAPIHandler extends BitzHandler<BitzAPI> {

	private Class<?>			packetWindowItems	= Reflection.getClass("{nms}.PacketPlayOutWindowItems");
	private FieldAccessor<?>	itemsField			= Reflection.getField(packetWindowItems, Object[].class, 0);

	private Class<?>			packetSetSlot		= Reflection.getClass("{nms}.PacketPlayOutSetSlot");
	private FieldAccessor<?>	itemField			= Reflection.getField(packetSetSlot, Reflection.getClass("{nms}.ItemStack"), 0);

	private Class<?>			craftItemStack		= Reflection.getClass("{obc}.inventory.CraftItemStack");
	private Class<?>			nmsItemStack		= Reflection.getClass("{nms}.ItemStack");

	private MethodInvoker		toNMS				= Reflection.getMethod(craftItemStack, "asNMSCopy", ItemStack.class);
	private MethodInvoker		toBukkit			= Reflection.getMethod(craftItemStack, "asBukkitCopy", nmsItemStack);

	private TinyProtocol		protocol;

	public BitzAPIHandler(BitzAPI plugin) {
		super(plugin);

		protocol = new TinyProtocol(plugin) {

			@Override
			public Object onPacketOutAsync(Player reciever, Channel channel, Object packet) {

				// Messing with the lore in creative == fails.
				// When in creative, not only does the lore get removed from
				// the
				// data sent to the client, but ALSO from the server data.
				// Weird, I know o.O
				if (reciever != null && reciever.getGameMode() == GameMode.CREATIVE) { return super.onPacketOutAsync(reciever, channel, packet); }

				if (packetSetSlot.isInstance(packet)) {

					System.out.println("PacketSetSlot");

					Object filtered = filterLore(itemField.get(packet));

					itemField.set(packet, filtered);

				} else if (packetWindowItems.isInstance(packet)) {

					System.out.println("PacketWindowItems");

					Object filtered = filterLore((Object[]) itemsField.get(packet));

					itemsField.set(packet, filtered);

				}

				return super.onPacketOutAsync(reciever, channel, packet);

			}

		};

	}

	private Object filterLore(Object[] items) {

		List<Object> output = ListUtils.empty();

		for (Object o : items) {

			Object fixed = filterLore(o);
			if (fixed != null) {
				output.add(fixed);
			} else {
				output.add(o);
			}

		}

		return output.toArray((Object[]) Array.newInstance(nmsItemStack, 0));

	}

	private Object filterLore(Object item) {

		if (item == null) { return null; }

		if (!nmsItemStack.isInstance(item)) {
			System.err.println("Expected NMS ItemStack, but found '" + item.getClass().getCanonicalName() + "'");
			return null;
		}

		ItemStack is = (ItemStack) toBukkit.invoke(null, item);
		System.out.println(is.toString());
		System.out.println("Clearing item lore...");
		LoreData.clearData(is);
		System.out.println(is.toString());
		System.out.println("Lore cleared!");

		return toNMS.invoke(null, is);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {

		if (!protocol.hasInjected(e.getPlayer())) {
			protocol.injectPlayer(e.getPlayer());
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onGamemodeChange(PlayerGameModeChangeEvent e) {

		if (e.getNewGameMode() == GameMode.CREATIVE) {

			PlayerWrapper player = $(e.getPlayer());

			ItemStack[] items = e.getPlayer().getInventory().getContents();

			for (int i = 0; i < items.length; i++) {

				if (!LoreData.hasData(items[i])) {
					continue;
				}

				System.out.println("Slot: " + i);
				Object packet = new PacketBuilder("PlayOutSetSlot").set("a", -2).set("b", i).set("c", toNMS.invoke(null, items[i])).create();

				player.sendPacket(packet);

			}

		}

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
