
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.commands.CommandUtils;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;
import com.rayzr522.bitzapi.utils.world.ItemUtils;

@CommandInfo(name = "rename", usage = "/bitz rename <text>", desc = "Rename your current item", pattern = "rename", perm = "bitzapi.item.rename")
public class BitzCommandRename implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!CommandUtils.isPlayer(sender)) {

		return true;

		}

		Player player = (Player) sender;

		if (args.length < 1) {

			plugin.messenger.playerMessage(player, BitzMessages.NO_ARG.msg, "text");
			return false;

		}

		if (ItemUtils.isEmpty(player.getItemInHand())) {

			plugin.messenger.playerInfo(player, "You have to be holding an item");
			return true;

		}

		ItemUtils.setName(player.getItemInHand(), ArrayUtils.concatArray(args, " "));

		return true;
	}

}
