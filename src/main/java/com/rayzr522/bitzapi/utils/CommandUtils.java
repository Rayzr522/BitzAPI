
package com.rayzr522.bitzapi.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUtils {

	public static boolean isPlayer(CommandSender sender) {

		return (sender instanceof Player);

	}

}
