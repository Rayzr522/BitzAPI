
package com.rayzr522.bitzapi.commands.bitz.item;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.commands.CommandUtils;
import com.rayzr522.bitzapi.utils.commands.TextUtils;
import com.rayzr522.bitzapi.utils.world.ItemUtils;

@CommandInfo(name = "dorf", usage = "/{command} item dorf", desc = "Dorf an item", pattern = "dorf", perm = "{base}.item.dorf")
public class BitzCommandItemDorf implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!CommandUtils.isPlayer(sender)) {

			plugin.messenger.playerMessage(sender, BitzMessages.ONLY_PLAYERS.msg);
			return true;

		}

		Player player = (Player) sender;
		if (ItemUtils.isEmpty(player.getItemInHand())) {

		return plugin.messenger.playerMessage(player, BitzMessages.NOT_HOLDING_ITEM.msg);

		}

		ItemStack item = player.getItemInHand();

		String name = TextUtils.color("&0&f&0&f&k:::&bDORF&k:::");

		ItemUtils.setName(item, name);

		return true;
	}

}
