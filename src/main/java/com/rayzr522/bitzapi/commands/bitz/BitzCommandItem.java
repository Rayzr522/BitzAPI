
package com.rayzr522.bitzapi.commands.bitz;

import org.bukkit.command.CommandSender;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.BitzCommand;
import com.rayzr522.bitzapi.commands.CommandHandler;
import com.rayzr522.bitzapi.commands.CommandInfo;
import com.rayzr522.bitzapi.commands.bitz.item.BitzCommandItemRename;
import com.rayzr522.bitzapi.commands.bitz.item.BitzCommandItemType;

@CommandInfo(name = "item", usage = "/{command} item", desc = "Item commands", pattern = "item", perm = "{base}.item")
public class BitzCommandItem implements BitzCommand {

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

		handler.registerCommand(BitzCommandItemRename.class);
		handler.registerCommand(BitzCommandItemType.class);

	}

}
