
package com.rayzr522.bitzapi.commands.bitz.fun;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.message.BitzMessages;
import com.rayzr522.bitzapi.utils.ActionBarUtil;
import com.rayzr522.bitzapi.utils.TextUtils;
import com.rayzr522.bitzapi.utils.data.ArrayUtils;

@CommandInfo(name = "ab", usage = "/{command} fun ab <player> <message>", desc = "Send an actionbar message to yourself", pattern = "ab(msg)?", perm = "{base}.fun.abmsg")
public class BitzCommandFunABMsg implements BitzCommand {

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (args.length < 1) {

		return plugin.messenger.playerMessage(sender, BitzMessages.NO_ARG.msg, "player");

		}

		if (args.length < 2) {

		return plugin.messenger.playerMessage(sender, BitzMessages.NO_ARG.msg, "message");

		}

		Player player = Bukkit.getPlayer(args[0]);

		if (player == null) {

		return plugin.messenger.playerMessage(sender, BitzMessages.NO_SUCH_PLAYER.msg, args[1]);

		}

		ActionBarUtil.sendActionBar(TextUtils.colorize(ArrayUtils.concatArray(ArrayUtils.removeFirst(args), " ")), player);

		return true;

	}

}
