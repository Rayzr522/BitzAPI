
package com.rayzr522.bitzapi.commands.bitz;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.utils.world.BlockUtils;
import com.rayzr522.bitzapi.utils.world.RegionUtils;
import com.rayzr522.bitzapi.world.PartialRegion;
import com.rayzr522.bitzapi.world.Region;

@CommandInfo(name = "show", usage = "/{command} show [reg:loc:locs]", desc = "Show the selections of one or all of the Bitz Tools", pattern = "sh(ow)?", perm = "{base}.tools")
public class BitzCommandShow implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!(sender instanceof Player)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;

		if (args.length < 1) {

			showRegion(player, plugin);
			showLocation(player, plugin);
			showLocationList(player, plugin);

		} else {

			if (args[0].equals("reg")) {

				showRegion(player, plugin);
				plugin.messenger.playerInfo(player, "Showing region");

			}

			else

			if (args[0].equals("loc")) {

				showLocation(player, plugin);
				plugin.messenger.playerInfo(player, "Showing location");

			}

			else

			if (args[0].equals("locs")) {

				showLocationList(player, plugin);
				plugin.messenger.playerInfo(player, "Showing locations list");

			}

			else {

				return false;

			}

		}

		return true;
	}

	private void showRegion(Player player, BitzPlugin plugin) {

		PartialRegion partial = BitzData.getRegionSelection(player);
		if (!partial.isComplete()) {

		return;

		}
		
		Region region = partial.toRegion();

		BlockUtils.showGhostBlocks(RegionUtils.getFrame(region), player, Material.WOOL, (byte) 14, 10, plugin);

	}

	private void showLocation(Player player, BitzPlugin plugin) {

		Location loc = BitzData.getLocationSelection(player);
		if (loc == null) {

		return;

		}

		BlockUtils.showGhostBlock(loc.getBlock(), player, Material.WOOL, (byte) 11, 10, plugin);

	}

	private void showLocationList(Player player, BitzPlugin plugin) {

		List<Location> locs = BitzData.getLocationListSelection(player);
		if (locs.size() < 1) {

		return;

		}

		List<Block> blocks = new ArrayList<Block>();

		for (Location loc : locs) {

			blocks.add(loc.getBlock());

		}

		BlockUtils.showGhostBlocks(blocks, player, Material.WOOL, (byte) 5, 10, plugin);

	}

}
