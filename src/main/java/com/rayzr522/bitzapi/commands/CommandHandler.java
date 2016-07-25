
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
import com.rayzr522.bitzapi.utils.data.ArrayUtils;

/**
 * 
 * @author PeterBlood
 * @see BitzCommand
 *
 */
public class CommandHandler implements CommandExecutor {

	private HashMap<String, BitzCommand>	commands;
	private String							baseCommand;
	private String							basePerm;
	private BitzPlugin						plugin;

	private boolean showCommandsWithNoAccess = true;

	/**
	 * @param autoSetup
	 *            set this to true if your first command in your plugin.yml file
	 *            is your main command to be handled with this handler
	 */
	public CommandHandler(BitzPlugin plugin, boolean autoSetup, String basePerm) {

		this.commands = new HashMap<String, BitzCommand>();
		this.plugin = plugin;

		if (autoSetup) {

			autoSetup();

		}

		this.basePerm = basePerm;

	}

	public CommandHandler(BitzPlugin plugin, boolean autoSetup) {

		this(plugin, autoSetup, plugin.getName().toLowerCase());

	}

	public CommandHandler(CommandHandler parent) {

		this(parent.plugin, false, parent.basePerm);

		this.baseCommand = parent.baseCommand;

	}

	/**
	 * This should only be used if you are creating a sub-command-handler that
	 * is one layer deep If your hierarchy goes
	 * <p>
	 * {@code Main Handler -> Sub Handler -> This Handler}
	 * </p>
	 * then don't use this method. This only works for
	 * <p>
	 * {@code Main Handler -> This Handler}
	 * </p>
	 * 
	 * @param parentHandlerPlugin
	 */
	public CommandHandler(BitzPlugin parentHandlerPlugin) {

		this(parentHandlerPlugin.getCommandHandler());

	}

	public void autoSetup(CommandHandler parent) {

		this.baseCommand = parent.baseCommand;

	}

	public void autoSetup() {

		String[] commands = this.plugin.getDescription().getCommands().keySet().toArray(new String[0]);
		if (commands.length > 0) {

			String command = commands[0];

			executorFor(command);

		}

	}

	/**
	 * Should the help messages show commands that you don't have permission to
	 * use?
	 * 
	 * @param yes
	 */
	public void showCommandsWithNoAccess(boolean yes) {

		this.showCommandsWithNoAccess = yes;

	}

	public void executorFor(String command) {

		if (plugin.getCommand(command) == null) { return; }

		baseCommand = command;
		plugin.getCommand(command).setExecutor(this);

	}

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

		String cmd = getCmd(args);
		String last = getLast(args);

		if (sender instanceof Conversable && ((Conversable) sender).isConversing()) { return true; }

		if (cmd.equals("")) {

			showHelp(sender);
			return true;

		}

		if (cmd.equals("?") || cmd.equals("help")) {

			showHelp(sender);
			return true;

		}

		List<BitzCommand> matches = getMatchingCommands(cmd);

		while (matches.size() < 1 && args.length > 1) {
			
			cmd += " ";
			cmd += args[1];
			args = trimFirstArg(args);
			last = getLast(args);

			plugin.logger.info("Trying new cmd: " + cmd);
			plugin.logger.info("New args: " + ArrayUtils.concatArray(args, ", "));

			matches = getMatchingCommands(cmd);

		}

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

		if (!this.plugin.has(sender, perm(info))) {

			plugin.messenger.playerMessage(sender, BitzMessages.NO_PERMISSION.msg, perm(info));
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

	private String getCmd(String[] args) {
		return (args.length > 0) ? args[0].toLowerCase() : "";
	}

	private String getLast(String[] args) {
		return (args.length > 0) ? args[(args.length - 1)] : "";
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

			boolean hasPerm = this.plugin.has(sender, perm(info));

			if (!hasPerm && !showCommandsWithNoAccess) {

				continue;

			}

			StringBuilder message = new StringBuilder();

			message.append(ChatColor.WHITE + usage(info));
			message.append(" | ");
			message.append(ChatColor.YELLOW + info.desc());

			if (!hasPerm) {

				message.append(ChatColor.WHITE + " | ");
				message.append(ChatColor.RED + "Needs \"" + perm(info) + "\"");

			}

			plugin.messenger.playerRaw(sender, message.toString());

		}

	}

	// Show usage for a command
	private void showUsage(CommandSender sender, BitzCommand command) {

		plugin.messenger.playerTitle(sender, "Usage:");

		CommandInfo info = (CommandInfo) command.getClass().getAnnotation(CommandInfo.class);

		StringBuilder message = new StringBuilder();

		message.append(ChatColor.WHITE + usage(info));
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

	public void fromExecute(CommandSender sender, String[] args) {

		onCommand(sender, null, null, args);

	}

	private String[] trimFirstArg(String[] args) {

		if (args == null) return new String[] {};

		return args.length > 0 ? (String[]) Arrays.copyOfRange(args, 1, args.length) : args;

	}

	public String getBasePerm() {
		return basePerm;
	}

	public void setBasePerm(String basePerm) {
		this.basePerm = basePerm;
	}

	public String usage(CommandInfo info) {

		if (baseCommand == null) {
			plugin.logger.error("This CommandHandler was not setup properly!");
			return "";
		}

		if (info == null || info.usage() == null) { return ""; }

		return info.usage().replace("{command}", baseCommand);

	}

	public String perm(CommandInfo info) {

		if (basePerm == null) {
			plugin.logger.error("This CommandHandler was not setup properly!");
			return "";
		}

		if (info == null || info.perm() == null) { return ""; }

		return info.perm().replace("{base}", basePerm);

	}

}
