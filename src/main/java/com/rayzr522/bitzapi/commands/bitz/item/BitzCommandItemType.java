
package com.rayzr522.bitzapi.commands.bitz.item;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.CommandUtils;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;
import com.rayzr522.bitzapi.utils.item.ItemUtils;

@CommandInfo(name = "type", usage = "/{command} item type <type>", desc = "Change the type of an item", pattern = "type", perm = "{base}.item.type")
public class BitzCommandItemType implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!CommandUtils.isPlayer(sender)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;

		if (args.length < 1) {

		return plugin.messenger.playerMessage(player, BitzMessages.NO_ARG.msg, "type");

		}

		String matString = ArrayUtils.concatArray(args, " ");
		Material mat = ItemUtils.getType(matString);

		if (mat == null) {

		return plugin.messenger.playerMessage(player, BitzMessages.NO_SUCH_MATERIAL.msg, matString);

		}

		if (ItemUtils.isEmpty(player.getInventory().getItemInMainHand())) {

		return plugin.messenger.playerMessage(player, BitzMessages.NOT_HOLDING_ITEM.msg);

		}

		ItemStack item = player.getInventory().getItemInMainHand();
		item.setType(mat);

		return true;
	}

}
