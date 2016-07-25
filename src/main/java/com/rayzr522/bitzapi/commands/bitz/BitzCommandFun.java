
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.command.CommandSender;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandHandler;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.commands.bitz.fun.BitzCommandFunABMsg;
import com.rayzr522.bitzapi.commands.bitz.fun.BitzCommandFunFling;
import com.rayzr522.bitzapi.commands.bitz.fun.BitzCommandFunPoof;

@CommandInfo(name = "fun", usage = "/{command} fun", desc = "Fun commands", pattern = "f(un)?", perm = "{base}.fun")
public class BitzCommandFun implements BitzCommand {

	CommandHandler handler;

	public boolean execute(CommandSender sender, String[] args, BitzPlugin plugin) {

		if (handler == null) {
			setup(plugin);
		}

		handler.fromExecute(sender, args);

		return true;
	}

	private void setup(BitzPlugin plugin) {

		handler = new CommandHandler(plugin);
		
		handler.showCommandsWithNoAccess(false);

		handler.registerCommand(BitzCommandFunFling.class);
		handler.registerCommand(BitzCommandFunPoof.class);
		handler.registerCommand(BitzCommandFunABMsg.class);

	}

}
