
package com.rayzr522.bitzapi.commands.bitz.item;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.CommandUtils;
import com.rayzr522.bitzapi.utils.TextUtils;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;
import com.rayzr522.bitzapi.utils.item.ItemUtils;

@CommandInfo(name = "rename", usage = "/{command} item rename <name>", desc = "Rename an item", pattern = "rename|name", perm = "{base}.item.rename")
public class BitzCommandItemRename implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!CommandUtils.isPlayer(sender)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;

		if (args.length < 1) {

		return plugin.messenger.playerMessage(player, BitzMessages.NO_ARG.msg, "name");

		}

		if (ItemUtils.isEmpty(player.getInventory().getItemInMainHand())) {

		return plugin.messenger.playerMessage(player, BitzMessages.NOT_HOLDING_ITEM.msg);

		}

		ItemStack item = player.getInventory().getItemInMainHand();

		String name = TextUtils.colorize("&0&f&0&f" + ArrayUtils.concatArray(args, " "));

		ItemUtils.setName(item, name);

		return true;
	}

}
