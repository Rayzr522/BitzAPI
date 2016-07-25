
package com.rayzr522.bitzapi.commands.bitz;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.commands.CommandUtils;
import com.rayzr522.bitzapi.utils.data.BitzData;
import com.rayzr522.bitzapi.world.PartialRegion;

@CommandInfo(name = "clearsel", usage = "/{command} clearsel [reg:loc:locs]", desc = "Clear the selection of one or all of the Bitz Tools", pattern = "clear(sel)?|cs", perm = "{base}.tools")
public class BitzCommandClearSel implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {
		if (!CommandUtils.isPlayer(sender)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;

		if (args.length < 1) {

			BitzData.setRegionSelection(player, new PartialRegion());
			plugin.messenger.playerInfo(player, "Region selection cleared");
			BitzData.setLocationSelection(player, null);
			plugin.messenger.playerInfo(player, "Location selection cleared");
			BitzData.setLocationListSelection(player, new ArrayList<Location>());
			plugin.messenger.playerInfo(player, "Location list selection cleared");

		} else {

			if (args[0].equals("reg")) {

				BitzData.setRegionSelection(player, new PartialRegion());
				plugin.messenger.playerInfo(player, "Region selection cleared");

			}

			else

			if (args[0].equals("loc")) {

				BitzData.setLocationSelection(player, null);
				plugin.messenger.playerInfo(player, "Location selection cleared");

			}

			else

			if (args[0].equals("locs")) {

				BitzData.setLocationListSelection(player, new ArrayList<Location>());
				plugin.messenger.playerInfo(player, "Location list selection cleared");

			}

			else {

				return false;

			}

		}

		return true;
	}

}
