package com.rayzr522.bitzapi.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.client.BitzMessages;
import com.rayzr522.bitzapi.client.Messenger;

public class CommandHandler implements CommandExecutor {
	
	private HashMap<String, BitzCommand> commands;
	private BitzPlugin plugin;
	
	/**
	 * @param autoSetup set this to true if your first command in your plugin.yml file is your main command to be handled with this handler
	 */
	public CommandHandler(BitzPlugin plugin, boolean autoSetup) {
		
		this.commands = new HashMap<String, BitzCommand>();
		this.plugin = plugin;
		
		if (autoSetup) {
			
			autoSetup();
			
		}
		
	}
	
	public void autoSetup() {
		
		String[] commands = this.plugin.getDescription().getCommands().keySet().toArray(new String[0]);
		if (commands.length > 0) {
			
			String command = commands[0];
			
			this.plugin.getCommand(command).setExecutor(this);
			
		}
		
	}

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		String cmd = (args.length > 0) ? args[0].toLowerCase() : "";
		String last = (args.length > 0) ? args[(args.length - 1)] : "";
		
		if (((sender instanceof Conversable)) && (((Conversable) sender).isConversing())) {
			return true;
		}
		
		if (cmd.equals("")) {
			
			showHelp(sender);
			return true;
			
		}
		
		if (cmd.equals("?") || cmd.equals("help")) {
			
			showHelp(sender);
			return true;
			
		}
		
		List<BitzCommand> matches = getMatchingCommands(cmd);
		
		if (matches.size() == 0) {
			
			plugin.messenger.playerMessage(sender, BitzMessages.NO_MATCHES.msg);
			return true;
			
		}
		
		if (matches.size() > 1) {
			
			plugin.messenger.playerMessage(sender, BitzMessages.MULTIPLE_MATCHES.msg);
			
			for (BitzCommand bitzCommand : matches) {
				
				showUsage(sender, bitzCommand);
				
			}
			
			return true;
			
		}
		
		BitzCommand command = matches.get(0);
		CommandInfo info = (CommandInfo) command.getClass().getAnnotation(CommandInfo.class);
		
		if (!this.plugin.has(sender, info.perm())) {
			
			plugin.messenger.playerWarning(sender, BitzMessages.NO_PERMISSION.msg.format(info.perm()));
			return true;
			
		}
		
		if (last.equals("?") || last.equals("help")) {
			
			showUsage(sender, command);
			return true;
			
		}
		
		String[] params = trimFirstArg(args);
		
		if (!command.execute(sender, params, plugin)) {
			
			showUsage(sender, command);
			
		}
		
		return true;
	}
	
	private List<BitzCommand> getMatchingCommands(String cmd) {
		
		List<BitzCommand> matches = new ArrayList<BitzCommand>();
		
		for (Entry<String, BitzCommand> entry : this.commands.entrySet()) {
			
			if (cmd.matches(entry.getKey())) {
				
				matches.add(entry.getValue());
				
			}
			
		}
		
		return matches;
		
	}
	
	// Show help messages
	// Format: 'usage : description'
	// Example: 'miner join <arena> : Join an arena'
	private void showHelp(CommandSender sender) {
		
		plugin.messenger.playerRaw(sender, Messenger.TITLE + " ----- COMMANDS ----- ");
		
		for (BitzCommand command : this.commands.values()) {
			
			CommandInfo info = (CommandInfo) command.getClass().getAnnotation(CommandInfo.class);
			
			StringBuilder message = new StringBuilder();
			
			message.append(ChatColor.WHITE + info.usage());
			message.append(" | ");
			message.append(ChatColor.YELLOW + info.desc());
			
			if (!this.plugin.has(sender, info.perm())) {
				
				message.append(ChatColor.WHITE + " | ");
				message.append(ChatColor.RED + "Needs \"" + info.perm() + "\"");
				
			}
			
			plugin.messenger.playerRaw(sender, message.toString());
			
		}
		
	}
	
	// Show usage for a command
	private void showUsage(CommandSender sender, BitzCommand command) {
		
		plugin.messenger.playerTitle(sender, "Usage:");
		
		CommandInfo info = (CommandInfo) command.getClass().getAnnotation(CommandInfo.class);
		
		StringBuilder message = new StringBuilder();
		
		message.append(ChatColor.WHITE + info.usage());
		message.append(" | ");
		message.append(ChatColor.YELLOW + info.desc());
		
		plugin.messenger.playerRaw(sender, message.toString());
		
	}
	
	public boolean isRegistered(String command) {
		
		return this.commands.containsKey(command);
		
	}
	
	public void registerCommand(Class<? extends BitzCommand> commandClass) {
		
		CommandInfo info = (CommandInfo) commandClass.getAnnotation(CommandInfo.class);
		
		if (info == null) {
			
			plugin.logger.warning("The command class '" + commandClass.getName() + "' has no command info. Please contact the plugin author.");
			
			return;
			
		}
		
		try {
			
			this.commands.put(info.pattern(), commandClass.newInstance());
			
		} catch (Exception e) {
			plugin.logger.warning("Something went wrong while trying to register the command class '" + commandClass.getName() + "'. Please contact the plugin author.");
			e.printStackTrace();
		}
	
	}
	
	private String[] trimFirstArg(String[] args) {
		
		return (String[]) Arrays.copyOfRange(args, 1, args.length);
		
	}

}
