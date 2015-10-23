
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.utils.ActionBarUtil;
import com.rayzr522.bitzapi.utils.commands.CommandUtils;
import com.rayzr522.bitzapi.utils.commands.TextUtils;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;

@CommandInfo(name = "ab", usage = "/bitz ab <player> <message>", desc = "Send an actionbar message to yourself", pattern = "ab(msg)?", perm = "")
public class BitzCommandABMsg implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (!CommandUtils.isPlayer(sender)) {

		return true;

		}

		if (args.length < 1) {

			plugin.messenger.playerMessage(sender, BitzMessages.NO_ARG.msg, "player");
			return false;

		}

		if (args.length < 2) {

			plugin.messenger.playerMessage(sender, BitzMessages.NO_ARG.msg, "message");
			return false;

		}

		Player player = Bukkit.getPlayer(args[0]);

		if (player == null) {

			plugin.messenger.playerMessage(sender, BitzMessages.NO_SUCH_PLAYER.msg, args[1]);
			return false;

		}

		ActionBarUtil.sendActionBar(TextUtils.color(ArrayUtils.concatArray(ArrayUtils.removeFirst(args), ' ')), player);

		return true;
	}

}
