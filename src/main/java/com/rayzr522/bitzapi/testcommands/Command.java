
package com.rayzr522.bitzapi.testcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.rayzr522.bitzapi.utils.data.ArrayUtils;

/**
 * Created by Ethan on 8/6/16.
 */
public class Command implements CommandExecutor {

	private String					name;
	private Map<String, String>		msgs;
	private Map<String, Command>	subs;
	private PluginCommand			command;
	private CommandExecutor			commandExecutor;
	private String					regex;
	private String					desc;
	private String					usage;

	/**
	 * @param name
	 * @param cme
	 * @param plugin
	 */
	public Command(String name, CommandExecutor cme) {
		this.commandExecutor = cme;
		this.name = name.toLowerCase();
		this.regex = name;
	}

	public Command(String name, PluginCommand command, CommandExecutor cme) {

		this(name, cme);

		this.command = command;
		if (this.command.getExecutor() == null) {
			this.command.setExecutor(this);
		}

	}

	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

		if (args.length > 1) {

			List<Command> matches = getMatches(args[0]);

			if (matches.size() > 0) {

				if (matches.size() > 1) {
					// TODO: What happens when there's multiple commands that
					// fit the bill?
				}

				return matches.get(0).onCommand(sender, cmd, args[0].toLowerCase(), ArrayUtils.removeFirst(args));

			}

		}

		commandExecutor.onCommand(sender, cmd, label, args);

		return true;
	}

	public List<Command> getMatches(String param) {

		List<Command> matches = new ArrayList<Command>();

		for (Command sub : subs.values()) {
			if (param.matches(sub.getRegex())) {
				matches.add(sub);
			}
		}

		return matches;

	}

	public void addSubcommand(Command cmd) {
		if (cmd.getPluginCommand() != command) {
			cmd.setPluginCommand(command);
		}
		subs.put(cmd.getName(), cmd);
	}

	public Command addSubcommand(String name, CommandExecutor cme) {

		Command cmd = new Command(name, command, cme);
		addSubcommand(cmd);
		return cmd;

	}

	public void setPluginCommand(PluginCommand command) {
		this.command = command;
	}

	public PluginCommand getPluginCommand() {
		return command;
	}

	public void putMessage(String key, String msg) {
		msgs.put(keyFormat(key), msg);
	}

	public Map<String, String> getMessageMap() {
		return msgs;
	}

	public void setMessages(Map<String, String> msgs) {
		this.msgs = msgs;
	}

	public String getMsg(String key) {
		return msgs.get(keyFormat(key));
	}

	/**
	 * 
	 * @param text
	 * @return the text formatted in a consistent way
	 */
	public String keyFormat(String text) {

		return text.toUpperCase().replaceAll(" ", "_");

	}

	/**
	 * 
	 * @return the Bukkit command that this is associated with. If this is a
	 *         subcommand it will be the same as the root's command
	 */
	public org.bukkit.command.Command getBukkitCmd() {
		return command;
	}

	/**
	 * Sets this to be the executor for the {@code command}. If the usage or
	 * description is undefined for this but is defined in the PluginCommand it
	 * copies it over to this.
	 * 
	 * @param command
	 * @return
	 */
	public Command executorFor(PluginCommand command) {
		this.command = command;

		command.setExecutor(this);

		if (usage == null && command.getUsage() != null && command.getUsage().trim().length() > 0) {
			usage = command.getUsage();
		}

		if (desc == null && command.getDescription() != null && command.getDescription().trim().length() > 0) {
			desc = command.getDescription();
		}

		return this;
	}

	/**
	 * 
	 * @return this command's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This doesn't do anything for the root command
	 * 
	 * @param regex
	 * @return this command, useful for one-liners
	 */
	public Command setRegex(String regex) {

		this.regex = regex;
		return this;

	}

	/**
	 * 
	 * @return this command's regex. If none has been specified it defaults to
	 *         the name of the command
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * Set the usage of this command
	 * 
	 * @param usage
	 * @return this command, useful for one-liners
	 */
	public Command setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	/**
	 * Get the command usage
	 * 
	 * @return this command's usage, or null if none has been specified
	 */
	public String getUsage() {
		return usage;
	}

	/**
	 * Set the description of this command
	 * 
	 * @param desc
	 * @return this command, useful for one-liners
	 */
	public Command setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	/**
	 * Get the description
	 * 
	 * @return this command's description, or null if none has been specified
	 */
	public String getDesc() {
		return desc;
	}

}
